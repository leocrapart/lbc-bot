(ns lbc-bot.core
  (:gen-class)
  (:require [etaoin.api :as e]
            [etaoin.keys :as k]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(+ 1 1)


(def driver (e/chrome))

(e/go driver "https://leboncoin.fr")


;; use robot lib : https://github.com/Liverm0r/robot
;; and transfer autohotkey code here