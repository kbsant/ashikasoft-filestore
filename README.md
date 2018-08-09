# ashikasoft-filestore
Bare-bones map file store for prototypes or small projects in Clojure.
This stores a map in edn files for easy retrieval/manipulation.

# Usage
Give the filestore a path and a name:

    (require '[ashikasoft.filestore.core :as fs])
    ;; load a table, or create it if it doesn't exist.
    (def my-table (fs/init-table! "/tmp" "my-table"))

Data is held in an atom, so use swap! for CRUD operations.

    ;; add a couple rows
    (swap! (fs/data my-table) assoc :my-key 17 :another-key 42)
    ;; view the table
    (fs/view my-table)
    ;; {:my-key 17, :another-key 42}

Clojure's swap takes care of atomic operations. For example, update and delete in one go:

    ;; update and delete in a single atomic operation
    (swap! (fs/data my-table)
           (comp #(update % :my-key inc) #(dissoc % :another-key)))
    ;; view it again
    (fs/view my-table)
    ;; {:my-key 18}

Write to disk.

    ;; Dump as an edn file in the directory.
    (fs/write-table! my-table)

# License
MIT License. No warranty.
