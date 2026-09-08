(ns recruit.murakumo
  "Pure cljc actor boundary generated from manifest migration scaffold."
  (:require [kotoba.lang.text :as str]))

(def actor-did
  "did:web:recruit.etzhayyim.com")

(def common-gates
  [:council-charter-attestation
   :no-platform-held-key-baseline
   :no-probing-baseline
   :murakumo-only-inference-baseline
   :did-primary-baseline
   :append-only-gate-baseline
   :kotoba-only-substrate-baseline])

(defn collection
  [name]
  (str "com.etzhayyim.recruit." name))

(def cell-specs {
  :ingesttaxonomy {:legacy-cell "com-etzhayyim-apps-recruit-ingestTaxonomy"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "ingesttaxonomy")]
     :required-gates common-gates
     :trigger "manifest cell ingesttaxonomy"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :ingestjobpostings {:legacy-cell "com-etzhayyim-apps-recruit-ingestJobPostings"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "ingestjobpostings")]
     :required-gates common-gates
     :trigger "manifest cell ingestjobpostings"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :listpostings {:legacy-cell "com-etzhayyim-apps-recruit-listPostings"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "listpostings")]
     :required-gates common-gates
     :trigger "manifest cell listpostings"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :listjobingestruns {:legacy-cell "com-etzhayyim-apps-recruit-listJobIngestRuns"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "listjobingestruns")]
     :required-gates common-gates
     :trigger "manifest cell listjobingestruns"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :getposting {:legacy-cell "com-etzhayyim-apps-recruit-getPosting"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "getposting")]
     :required-gates common-gates
     :trigger "manifest cell getposting"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :stats {:legacy-cell "com-etzhayyim-apps-recruit-stats"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "stats")]
     :required-gates common-gates
     :trigger "manifest cell stats"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :recommendcohorts {:legacy-cell "com-etzhayyim-apps-recruit-recommendCohorts"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "recommendcohorts")]
     :required-gates common-gates
     :trigger "manifest cell recommendcohorts"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :matchstats {:legacy-cell "com-etzhayyim-apps-recruit-matchStats"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "matchstats")]
     :required-gates common-gates
     :trigger "manifest cell matchstats"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :proposecohortmatch {:legacy-cell "com-etzhayyim-apps-recruit-proposeCohortMatch"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "proposecohortmatch")]
     :required-gates common-gates
     :trigger "manifest cell proposecohortmatch"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :listmatchproposals {:legacy-cell "com-etzhayyim-apps-recruit-listMatchProposals"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "listmatchproposals")]
     :required-gates common-gates
     :trigger "manifest cell listmatchproposals"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :getmatchproposal {:legacy-cell "com-etzhayyim-apps-recruit-getMatchProposal"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "getmatchproposal")]
     :required-gates common-gates
     :trigger "manifest cell getmatchproposal"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :decidematchproposal {:legacy-cell "com-etzhayyim-apps-recruit-decideMatchProposal"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "decidematchproposal")]
     :required-gates common-gates
     :trigger "manifest cell decidematchproposal"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :listmatchdecisionevents {:legacy-cell "com-etzhayyim-apps-recruit-listMatchDecisionEvents"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "listmatchdecisionevents")]
     :required-gates common-gates
     :trigger "manifest cell listmatchdecisionevents"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :shinkaevolution {:legacy-cell "com-etzhayyim-apps-standard-shinkaEvolution"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "shinkaevolution")]
     :required-gates common-gates
     :trigger "manifest cell shinkaevolution"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :shinkaknowledge {:legacy-cell "com-etzhayyim-apps-standard-shinkaKnowledge"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "shinkaknowledge")]
     :required-gates common-gates
     :trigger "manifest cell shinkaknowledge"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :shinka {:legacy-cell "shinka"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "shinka")]
     :required-gates common-gates
     :trigger "manifest cell shinka"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :koji {:legacy-cell "koji"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "koji")]
     :required-gates common-gates
     :trigger "manifest cell koji"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :kyumei {:legacy-cell "kyumei"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "kyumei")]
     :required-gates common-gates
     :trigger "manifest cell kyumei"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :domain-knowledge {:legacy-cell "domain-knowledge"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "domain-knowledge")]
     :required-gates common-gates
     :trigger "manifest cell domain-knowledge"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :jobposting {:legacy-cell "com-etzhayyim-apps-recruit-jobPosting"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "jobposting")]
     :required-gates common-gates
     :trigger "manifest cell jobposting"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :occupation {:legacy-cell "com-etzhayyim-apps-recruit-occupation"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "occupation")]
     :required-gates common-gates
     :trigger "manifest cell occupation"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
  :entity {:legacy-cell "com-etzhayyim-legalEntity-entity"
     :phase :event
     :murakumo-node "reuben"
     :collections [(collection "entity")]
     :required-gates common-gates
     :trigger "manifest cell entity"
     :ceiling "Manifest-driven migration scaffold; explicit execution stays in runtime methods"}
})

(defn safe-rkey
  [s]
  (let [clean (-> (str s)
                  (str/replace #"^did:web:" "")
                  (str/replace #"[^A-Za-z0-9._~-]" "-"))]
    (if (str/blank? clean) "unknown" clean)))

(defn gate-value
  [attestations gate]
  (or (get attestations gate)
      (get attestations (name gate))
      (when (set? attestations) (attestations gate))
      (when (set? attestations) (attestations (name gate)))))

(defn missing-gates
  [spec attestations]
  (->> (:required-gates spec)
       (remove #(boolean (gate-value attestations %)))
       vec))

(defn put-record-effect
  [collection rkey record]
  {:op :mst/put-record
   :actor actor-did
   :collection collection
   :rkey rkey
   :record record})

(defn records-for
  [spec {:keys [records record computed-at request-id]
         :as input}]
  (let [input-records (cond
                        (map? records) records
                        (some? record) {0 record}
                        :else {})
        base {:actorDid actor-did
              :computedAt computed-at
              :legacyCell (:legacy-cell spec)
              :phase (:phase spec)
              :requestId request-id
              :actorBoundary "cljc-migration-scaffold"
              :scaffold true
              :constitutionalStatus "attested-plan"}]
    (map-indexed
     (fn [idx coll]
       (let [record* (merge {:$type coll}
                            base
                            (or (get input-records coll)
                                (get input-records idx)
                                {}))
             rkey (safe-rkey (or (:rkey record*)
                                 (get record* "rkey")
                                 (:tid record*)
                                 request-id
                                 (str (:legacy-cell spec) "-" idx)))]
         {:collection coll
          :record record*
          :rkey rkey}))
     (:collections spec))))

(defn cell-plan
  [cell-key {:keys [attestations] :as input}]
  (let [spec (get cell-specs cell-key)]
    (when-not spec
      (throw (ex-info "unknown cell" {:cell cell-key})))
    (let [missing (missing-gates spec attestations)]
      (merge
       {:cell cell-key
        :legacy-cell (:legacy-cell spec)
        :actor actor-did
        :phase (:phase spec)
        :murakumo-node (:murakumo-node spec)
        :trigger (:trigger spec)
        :ceiling (:ceiling spec)
        :required-gates (:required-gates spec)
        :missing-gates missing}
       (if (seq missing)
         {:status :blocked
          :effects []}
         (let [planned-records (records-for spec input)]
           {:status :ready
            :records (vec planned-records)
            :effects (mapv (fn [{:keys [collection record rkey]}]
                             (put-record-effect collection rkey record))
                           planned-records)}))))))

(defn all-cell-plans
  [input]
  (into {}
        (map (fn [cell-key] [cell-key (cell-plan cell-key input)]))
        (keys cell-specs)))
