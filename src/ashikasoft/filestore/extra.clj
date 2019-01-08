(ns ashikasoft.filestore.extra
  (:require
    [ashikasoft.filestore.impl :as impl]))

(def join-path
  "Utility function to join paths with the os separator."
  impl/join-path)

(def last-childname
  "Get the last filename in a directory."
  impl/last-childname)

(def next-childname
  "Get the next filename in a directory."
  impl/next-childname)

