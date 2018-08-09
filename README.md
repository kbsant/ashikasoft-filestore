# ashikasoft-filestore
Bare-bones kv file store for prototypes or small projects in Clojure

# Usage
Give the filestore a path and a name:

    (require '[ashikasoft.filestore.core :as fs])
    ;; read a table, or create it if it doesn't exist.
    (def my-table (fs/read-table "/tmp" "my-table"))
    ;; add some data
    (swap! (:data my-table) assoc :my-key 17)
    ;; view the table
    (fs/view my-table)
    ;; update the data
    (swap! (:data my-table) update :my-key inc)
    ;; view it again
    (fs/view my-table)
    ;; write to disk
    (fs/write-table my-table)

# License
MIT License. No warranty.
