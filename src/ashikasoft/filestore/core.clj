(ns ashikasoft.filestore.core
  (:require
    [ashikasoft.filestore.impl :as impl]
    [clojure.edn :as edn]
    [clojure.java.io :as io]))

;; Bare-bones edn file store, meant for prototypes or small projects.
;; Does not include queuing or caching.

;; declare plugin multimethods
(defmulti plugin-do-init! (fn [store plugin-type] plugin-type))
(defmethod plugin-do-init! :default [store _] store)

(defmulti plugin-do-write! (fn [store plugin-type] plugin-type))
(defmethod plugin-do-write! :default [store _] store)

(defn init-store!
  "Initialize a store with data from the directory.
   If the directory is missing, it will be created and initialized
   with the given init-data, or an empty map if init-data is omitted."
  ([base-dir name]
   (init-store! base-dir name {}))
  ([base-dir name init-data]
   (let [loc-info (impl/init-loc! base-dir name)
         data (impl/read-file loc-info)]
     {:loc-info loc-info
      :plugins []
      :plugin-data {}
      :data (atom (or data init-data))})))

(defn load-plugin! [store {plugin-type :plugin-type :as plugin}]
  (let [store-with-plugin (-> store
                              (update :plugins conj plugin-type)
                              (assoc-in [:plugin-data plugin-type] plugin))]
    (plugin-do-init! store-with-plugin plugin-type)))

(defn init-plugins!
  "Add plugin information to the store and initialize the plugins. This may modify the store."
  [store plugins]
  (reduce load-plugin! store plugins))

(defn write-store!
  "Write the store data to a file. Invoke the plugins' write methods on the store, if any."
  [{:keys [loc-info data plugins] :as store}]
  (impl/write-file! loc-info @data)
  (reduce plugin-do-write! store plugins))

(defn data
  "Return the atom containing the store data."
  [store]
  (:data store))

(defn view
  "View the store data."
  [store]
  (some-> store :data deref))
