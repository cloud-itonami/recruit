# operator quickstart

**このファイルのコマンドは全部、2026-09-06 にこの repo の `89adf3e` に対して
実際に実行し、下に載せた出力をその場で確認したものである。** 踏めなかった手順は
載せていない（載せるべき手順が無かったわけではなく、踏めないものを書かない）。

計測環境: macOS 26.3.1 / nbb v1.5.212 / Clojure CLI 1.12.5.1654。

前提: この repo を clone してその root に居ること。**外部サービスも credential も
DB も要らない** —— `src/recruit/murakumo.cljc` は純関数だけで、I/O を 1 つも
しないため。

---

## Step 0 — checkout

```bash
git clone git@github.com:cloud-itonami/recruit.git
cd recruit
```

west 管理下なら checkout は既に `orgs/cloud-itonami/recruit` に在る。

## Step 1 — 何が居るかを見る（JVM 不要、数秒）

```bash
nbb --classpath src -e '
(require (quote [recruit.murakumo :as m]))
(println (count m/cell-specs) "cells")
(prn (vec (sort (keys m/cell-specs))))'
```

観測した出力:

```
22 cells
[:decidematchproposal :domain-knowledge :entity :getmatchproposal :getposting
 :ingestjobpostings :ingesttaxonomy :jobposting :koji :kyumei :listjobingestruns
 :listmatchdecisionevents :listmatchproposals :listpostings :matchstats
 :occupation :proposecohortmatch :recommendcohorts :shinka :shinkaevolution
 :shinkaknowledge :stats]
```

この 22 個が `actor-manifest.jsonld` の pipeline / requiredCollections /
requiredLoops から起こされた cell である。

## Step 2 — 何も attest せずに計画を求める（= 門が閉まっていることを見る）

```bash
nbb --classpath src -e '
(require (quote [recruit.murakumo :as m]))
(let [plan (m/cell-plan :stats {})]
  (println "status        =" (:status plan))
  (println "effects       =" (count (:effects plan)))
  (println "missing-gates =" (count (:missing-gates plan)))
  (prn (:missing-gates plan)))'
```

観測した出力:

```
status        = :blocked
effects       = 0
missing-gates = 7
[:council-charter-attestation :no-platform-held-key-baseline :no-probing-baseline
 :murakumo-only-inference-baseline :did-primary-baseline :append-only-gate-baseline
 :kotoba-only-substrate-baseline]
```

**effect が 0 本であることが重要**で、`:blocked` は「後で書く」ではなく
「書くものを 1 つも作らない」を意味する。

## Step 3 — 7 gate を全部 attest して計画を求める

```bash
nbb --classpath src -e '
(require (quote [recruit.murakumo :as m]))
(let [att  (into {} (map (fn [g] [g "attested"])) m/common-gates)
      plan (m/cell-plan :stats {:attestations att :request-id "demo-1"})]
  (println "status        =" (:status plan))
  (println "missing-gates =" (count (:missing-gates plan)))
  (println "effects       =" (count (:effects plan)))
  (prn (first (:effects plan))))'
```

観測した出力:

```
status        = :ready
missing-gates = 0
effects       = 1
{:op :mst/put-record,
 :actor "did:web:recruit.etzhayyim.com",
 :collection "com.etzhayyim.recruit.stats",
 :rkey "demo-1",
 :record {:$type "com.etzhayyim.recruit.stats",
          :actorBoundary "cljc-migration-scaffold",
          :legacyCell "com-etzhayyim-apps-recruit-stats",
          :phase :event,
          :computedAt nil,
          :requestId "demo-1",
          :constitutionalStatus "attested-plan",
          :actorDid "did:web:recruit.etzhayyim.com",
          :scaffold true}}
```

`:rkey` が `"demo-1"`（= 渡した `:request-id`）になっていることに注意 ——
rkey の決め方は `safe-rkey` で、`did:web:` prefix を落とし
`[A-Za-z0-9._~-]` 以外を `-` に潰す。

**この effect は「書け」という指示であって、書き込みではない。** 実際に MST へ
put するのは呼び出し側で、このライブラリは `:op` を組み立てるところで止まる。

## Step 4 — 門が discriminate することを確かめる（省略しない）

**Step 2 と Step 3 だけでは「gate が効いている」証拠にならない** —— 全部無い
ときと全部在るときしか見ていないので、実は「attestations が非空なら通す」実装でも
同じ出力になる。7 つのうち **ちょうど 1 つ**を落とす:

```bash
nbb --classpath src -e '
(require (quote [recruit.murakumo :as m]))
(let [att  (into {} (map (fn [g] [g "attested"])) (rest m/common-gates))
      plan (m/cell-plan :stats {:attestations att :request-id "demo-2"})]
  (println "dropped       =" (first m/common-gates))
  (println "status        =" (:status plan))
  (println "effects       =" (count (:effects plan)))
  (prn (:missing-gates plan)))'
```

観測した出力:

```
dropped       = :council-charter-attestation
status        = :blocked
effects       = 0
[:council-charter-attestation]
```

6/7 attested でも `:blocked`、effect 0 本、そして**落とした gate の名前がそのまま
返る**。これで門は「閉まることがある」だけでなく「正しい理由で閉まる」と言える。

## Step 5 — テストを回す

2 経路あり、**どちらも実際に通した**。

### JVM を起こさない経路（速い。既定はこちら）

```bash
nbb --classpath src:test -e '
(require (quote [cljs.test :as t]) (quote [recruit.murakumo-test]))
(t/run-tests (quote recruit.murakumo-test))'
```

観測した出力:

```
Testing recruit.murakumo-test

Ran 9 tests containing 304 assertions.
0 failures, 0 errors.
```

`src/recruit/murakumo.cljc` は `clojure.string` しか require しない `.cljc` なので、
nbb でそのまま読める。

### deps.edn が宣言している経路（JVM）

```bash
clojure -M:test
```

観測した出力（`Ran 9 tests containing 304 assertions. 0 failures, 0 errors.`、
exit 0）。初回は cognitect test-runner の取得で時間がかかる。

⚠ **exit code を pipe 越しに読まないこと** —— `clojure -M:test | tail` の `$?` は
`tail` の終了値で、テストの値ではない。先にファイルへ落としてから読む:

```bash
clojure -M:test > /tmp/recruit-test.log 2>&1; echo "EXIT=$?"; tail -20 /tmp/recruit-test.log
```

## Step 6 — lint

```bash
clojure -M:lint > /tmp/recruit-lint.log 2>&1; echo "EXIT=$?"; tail -5 /tmp/recruit-lint.log
```

観測した出力: `EXIT=0` / `errors: 0, warnings: 1`（所要時間は run ごとに変わるので値を写さない）。
その 1 件は `src/recruit/murakumo.cljc:208:14: warning: unused binding input`
（`records-for` の `:as input`）で、既知・無害。**`--fail-level error` なので
warning では落ちない。**

---

## ここに無いもの（探して時間を溶かさないために）

同梱の `CLAUDE.md` は actor の仕様スナップショットで、**この checkout の操作手順
ではない**。2026-09-06 実測で、次はこの repo に存在しない:

| CLAUDE.md の記述 | この repo での実際 |
|---|---|
| `pnpm run recruit:jobs:ingest` | `package.json` が無い |
| `50-infra/k8s/recruit-job-ingester/` | `50-infra/` が無い |
| `90-docs/adr/0018-pii-tier3-cohort-first.md` | `90-docs/` が無い |
| RisingWave / `RW_CONN` を使う live smoke | DB クライアントが無い（依存は test-runner と clj-kondo だけ） |

これらは actor が etzhayyim monorepo に居た頃の面である。**この repo の
production source は `src/recruit/murakumo.cljc` 1 本だけ**（`git ls-files` で
9 ファイル、うち `src/` は 1 本）。
