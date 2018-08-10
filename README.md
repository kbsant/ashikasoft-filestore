# ashikasoft-filestore

[![Clojars Project](https://img.shields.io/clojars/v/ashikasoft/filestore.svg)](https://clojars.org/ashikasoft/filestore)

Bare-bones map file store for prototypes or small projects in Clojure.
This stores a map in edn files for easy retrieval/manipulation.

# Usage
Give the filestore a path and a name:

    (require '[ashikasoft.filestore.core :as fs])
    ;; load a data store, or create it if it doesn't exist.
    (def my-store (fs/init-store! "/tmp" "my-store"))
    ;; note that the above is equivalent to initializing the store with an empty map:
    ;; (fs/init-store! "/tmp" "my-store" {})

Data is held in an atom, so use swap! for CRUD operations.

    ;; add a couple rows
    (swap! (fs/data my-store) assoc :my-key 17 :another-key 42)
    ;; view the data
    (fs/view my-store)
    ;; {:my-key 17, :another-key 42}

Clojure's swap takes care of atomic operations. For example, update and delete in one go:

    ;; update and delete in a single atomic operation
    (swap! (fs/data my-store)
           (comp #(update % :my-key inc) #(dissoc % :another-key)))
    ;; view it again
    (fs/view my-store)
    ;; {:my-key 18}

Write to disk.

    ;; Dump as an edn file in the directory.
    (fs/write-store! my-store)

# License
MIT License. No warranty.
