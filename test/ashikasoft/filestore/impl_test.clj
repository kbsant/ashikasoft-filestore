(ns ashikasoft.filestore.impl-test
  (:require
   [clojure.string :as string]
   [clojure.test :refer :all]
   [ashikasoft.filestore.impl :refer :all]))

(deftest path-util-test
  (testing "remove-trailing-path"
    (testing "removes a trailing separator."
      (is (= "test" (remove-trailing-sep "test/"))))
    (testing "does nothing if separator is not trailing."
      (is (= "notrail/ing" "notrail/ing")))))
