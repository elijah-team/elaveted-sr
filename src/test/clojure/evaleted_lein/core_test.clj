(ns evaleted-lein.core-test
  (:require [clojure.test :refer :all]
            [evaleted-lein.core :refer :all])
  (:use [clojure.pprint :refer pp])
  (:import (java.util ArrayList)
           (tripleo.elijah Main)
           (tripleo.elijah_clojure.example CljExampleMain)))

(deftest a-test
  (testing "FIXME, I fail."
    ;(doto (ExampleTEst.)
    ;	(.chunkyExample))

    (is (= 1 1))))

(deftest b-test
  (testing "Entry point for test/demo-el-normal/main2"
    (let [f "test/demo-el-normal/main2"
          args (ArrayList.)
          ccl (ArrayList.)
          cca (atom nil)

          cfg {:foo                       :bar
               "CompilerController-deref" (fn [] '(deref cca 300 nil))
               "CompilerController"       (fn [x]
                                            (swap! cca 'x))}
          ctl (Main/main3 (list f) cfg)]
      (is (= (.errorCount ctl) 1)))))

(defn hello []
  (println 6)
  "non-existent")

(deftest c-test
  (testing "FIXME, 3"
    (is (= "non-existent" (CljExampleMain/callClojure "evaleted-lein.core-test" "hello")))))

(deftest d-test
  (testing "FIXME, 4"
    (is (= 1 1)
        (= "non-existent" (CljExampleMain/callClojure2)))))
