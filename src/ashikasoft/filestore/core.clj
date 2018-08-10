(ns ashikasoft.filestore.core
  (:require
    [ashikasoft.filestore.impl :as impl]
    [clojure.edn :as edn]
    [clojure.java.io :as io]))

;; Bare-bones edn file store, meant for prototypes or small projects.
;; Does not include queuing or caching.

(defn init-store!
  ([base-dir name]
   (init-store! base-dir name {}))
  ([base-dir name init-data]
   (let [loc-info (impl/init-loc! base-dir name)
         data (impl/read-file loc-info)]
     {:loc-info loc-info
      :data (atom (or data init-data))})))
    
(defn write-store! [{:keys [loc-info data] :as store}]
  (impl/write-file! loc-info @data))

(defn data [store]
  (:data store))

(defn view [store]
  (some-> store :data deref))

