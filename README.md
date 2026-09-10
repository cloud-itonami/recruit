# recruit — 求人 posting を法人 DID に錨で留める actor boundary

`recruit` は名前が機能を示さない bare な subject 名なので、最初に名乗る。

**この repo が持っているのは、`recruit` actor の *境界* を記述した pure `.cljc`
ライブラリ 1 本とその契約テストだけである。** 求人を実際に取ってくる ingester も、
それを動かす k8s worker も、graph store も、ここには**無い**。ここに在るのは
「どの cell が、どの gate を全部満たしたときに、どの collection へ何を書いてよいか」
を計算する純関数である。

```
src/recruit/murakumo.kotoba    ← 唯一の production source（22 cell の仕様 + plan 関数）
test/recruit/murakumo_test.kotoba
actor-manifest.jsonld        ← actor の宣言（pipeline / governance / data source allowlist）
.well-known/did.json         ← did:web:etzhayyim.com:actor:recruit
```

## 何をするライブラリか

`cell-plan` は **「実行」ではなく「計画」を返す**。7 つの gate
（`common-gates`）が全部 attested でなければ `:status :blocked` を返して
**effect を 1 つも出さない**。全部揃ったときだけ `:status :ready` と、
`:op :mst/put-record` の effect 列を返す。書き込みは呼び出し側の仕事で、
このライブラリは 1 バイトも I/O しない。

```
attestations 不足 → {:status :blocked :effects [] :missing-gates [...]}
attestations 充足 → {:status :ready   :effects [{:op :mst/put-record ...}]}
```

**gate は本当に閉まる。** 7 つのうち 1 つを落とすだけで `:blocked` になり、
落とした gate 名がそのまま `:missing-gates` に出る（実演は
[`docs/operator-quickstart.md`](docs/operator-quickstart.md) の Step 4）。

## 最近接 repo との境界

| repo | 何を持つか | こことの違い |
|---|---|---|
| `talent.etzhayyim.com` | candidate 側の matching | **PII を持つのはあちら。** ここは cohort 集計しか見ない |
| `isco.etzhayyim.com` | ISCO-08 occupation taxonomy | 職業コードの正本。ここは参照するだけ |
| `legal-entity.etzhayyim.com` | 法人実体（LEI / 法人番号） | posting の錨。ここは DID を持つだけで解決しない |

## ⚠ `CLAUDE.md` はこの repo の操作手順ではない

同梱の `CLAUDE.md` は **actor の仕様スナップショット**であって、この checkout で
踏める手順書ではない。そこに書かれている

- `pnpm run recruit:jobs:ingest` / `pnpm run recruit:jobs:dry-run`
- `50-infra/k8s/recruit-job-ingester/`
- `90-docs/adr/0018-pii-tier3-cohort-first.md`

は **どれもこの repo に存在しない**（`package.json` も `50-infra/` も `90-docs/` も
無い。2026-09-06 実測）。それらは actor が etzhayyim monorepo に居た頃の面である。
**この checkout で実際に踏める手順は
[`docs/operator-quickstart.md`](docs/operator-quickstart.md) が正本**で、そこに
書いてあるコマンドは全部、書いた時点で実行して出力を確認したものだけである。

## 30 秒で確かめる

```bash
nbb --classpath src:test -e '(require (quote [cljs.test :as t]) (quote [recruit.murakumo-test])) (t/run-tests (quote recruit.murakumo-test))'
# => Ran 9 tests containing 304 assertions. 0 failures, 0 errors.
```

JVM は要らない。詳しくは quickstart を参照。

## ライセンス

`NOTICE` を参照。
