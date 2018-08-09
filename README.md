# ashikasoft-filestore
Bare-bones kv file store for prototypes or small projects in Clojure

# Usage
Give the filestore a path and a name:

    (require '[ashikasoft.filestore.core :as fs])
    ;; read a table, or create it if it doesn't exist.
    (def my-table (fs/read-table "/tmp" "my-table"))

Data is held in an atom, so use swap! for CRUD operations.

    ;; add a couple rows
    (swap! (:data my-table) assoc :my-key 17 :another-key 42)
    ;; view the table
    (fs/view my-table)

Performing multiple operations atomically is taken care of by Clojure's swap.

    ;; update and delete in a single atomic operation
    (swap! (:data my-table)
           (comp #(update % :my-key inc) #(dissoc % :another-key)))
    ;; view it again
    (fs/view my-table)

Write to disk.

    ;; Dump as an edn file in the directory.
    (fs/write-table my-table)

# License
MIT License. No warranty.
