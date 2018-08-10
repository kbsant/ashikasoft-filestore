(ns ashikasoft.filestore.impl
  (:require
    [clojure.edn :as edn]
    [clojure.java.io :as io]))

;; Implementation file - not meant for public consumption.

(defn join-path [& elements]
  (clojure.string/join java.io.File/separator elements))

(defn last-childname
  "Get the current max name in the base directory"
  [base-dir]
  (->> (io/file base-dir)
    (.listFiles)
    (map #(.getName %))
    (sort)
    (last)))

(defn format-name [num] (format "%08d" num))

(defn parse-name [name] (Integer/parseInt name))

;; FIXME this isnt thread safe -- after the prototype, move to a db
(defn next-childname
  "Generate the next name after the max name in the base directory, or a new name"
  [base-dir]
  (or
    (some->> (last-childname base-dir)
      (parse-name)
      (inc)
      (format-name))
    (format-name 1)))

(defn init-loc!
  "Make base dir and return immutable info about the store (base dir and table name)" 
  [base-dir name]
  (let [path (join-path base-dir name)]
    (-> (io/file path) .mkdirs)
    {:base-dir base-dir
     :name name
     :path path}))

(defn child-path [{path :path :as loc-info} child-fn]
  (let [child-name (child-fn path)]
    (when child-name
      (join-path path child-name))))

(defn last-child-path [loc-info]
  (child-path loc-info last-childname))

(defn next-child-path [loc-info]
  (child-path loc-info next-childname))

(defn read-edn-file [filename]
  (when filename
    (-> (slurp filename) edn/read-string)))

(defn write-edn-file! [filename data]
  (when data
    (spit filename (prn-str data))))

(defn read-file [loc-info]
  (read-edn-file (last-child-path loc-info)))

(defn write-file! [loc-info data]
  (write-edn-file! (next-child-path loc-info) data))

