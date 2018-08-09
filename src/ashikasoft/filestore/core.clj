(ns ashikasoft.filestore.core
  (:require
    [ashikasoft.filestore.impl :as impl]
    [clojure.edn :as edn]
    [clojure.java.io :as io]))

;; Bare-bones edn file store, meant for prototypes or small projects.
;; Does not include queuing or caching.

(defn init-table! [base-dir table-name]
  (let [store (impl/init-store! base-dir table-name)
        data (impl/read-store store)]
    {:store-info store
     :data (atom (or data {}))}))
    
(defn write-table! [table]
  (let [{:keys [store-info data]} table]
    (impl/write-store! store-info @data)))

(defn data [table]
  (:data table))

(defn view [table]
  (some-> table :data deref))

