(ns lbc-bot.core
  (:gen-class)
  (:require [robot.core :as r]
            [dk.ative.docjure.spreadsheet :as xl]
            [clojure.edn :as edn]))



(defn sleep [ms]
  (Thread/sleep ms))

(defn click [x y]
  (sleep 100)
  (r/mouse-move! x y)
  (sleep 100)
  (r/mouse-click!)
  (sleep 100))

(defn paste [text]
  (sleep 1000)
  (r/clipboard-put! text)
  (sleep 100)
  (r/hot-keys! [:cmd :v])
  (sleep 100))

(defn double-click [x y]
  (r/mouse-move! x y)
  (sleep 100)
  (r/mouse-click!)
  (sleep 50)
  (r/mouse-click!)
  (sleep 100))


(defn triple-click [x y]
  (r/mouse-move! x y)
  (sleep 100)
  (r/mouse-click!)
  (sleep 50)
  (r/mouse-click!)
  (sleep 50)
  (r/mouse-click!)
  (sleep 100))

; (paste "leo")

;; data interface 

(def sheet
  (->> (xl/load-workbook "data.xlsx")
       (xl/select-sheet "bot")))

(defn cell [cell-str]
  (let [col (first cell-str)
        row (edn/read-string (str (last cell-str)))]
    (->> sheet
         (xl/select-cell cell-str)
         (xl/read-cell))))

; (cell "A1")

(defn title [n]
  (cell (str "A" (+ n 1))))

; (title 1)
; (title 5)
; (title 10)

(defn description [n]
  (cell (str "B" (+ n 1))))

; (description 1)

(defn image [n]
  (cell (str "C" (+ n 1))))

(defn location [n]
  (cell (str "D" (+ n 1))))

(def phone1
  (cell "G2"))

(def phone2
  (cell "H2"))

(def phone3
  (cell "I2"))

(def phone4
  (cell "J2"))

(def phone5
  (cell "K2"))

(def phone6
  (cell "L2"))

(def phone7
  (cell "M2"))

(def phone8
  (cell "N2"))

(def phone9
  (cell "O2"))

(def phone10
  (cell "P2"))

(def phone-id-to-phone
{1 phone1
 2 phone2
 3 phone3
 4 phone4
 5 phone5
 6 phone6
 7 phone7
 8 phone8
 9 phone9
 10 phone10
})

(defn phone [n]
  (let [phone-id (int (cell (str "E" (+ n 1))))]
    (phone-id-to-phone phone-id)))


(def email
  (cell "G8"))

(def password
  (cell "G11"))

; (phone 1)
; (phone 2)
; (phone 3)
; (phone 10)


(defn annonce [n]
{:title (title n)
 :description (description n)
 :image (image n)
 :location (location n)
 :phone (phone n)})

; (annonce 1)
; (annonce 10)
; ((annonce 10) :location)


;;--- end data interface ---


;;; init
; ;; go to leboncoin.fr
; (click 274 47)
; (sleep 500)
; (paste "leboncoin.fr")
; (r/type! :enter)
; (sleep 3000)

; ; accept cookie
; (click 873 715)
; (sleep 500)

;; login
; (click 1287 130)
; (sleep 1000)
; ; email
; (click 810 453)
; (paste "bretonphilippe643@gmail.com")
; ; password
; (click 810 550)
; (paste "Sdfghj234")
; ; connect
; (click 810 650)
; (sleep 1000)

; bretonphilippe643@gmail.com
; Sdfghj234

; https://docs.google.com/spreadsheets/d/1kJad2znXmaUv6ulb6dxbZwWxtFa81QL4/edit#gid=1848863505

(defn init []
  (click 398 84)
  (paste "https://leboncoin.fr")
  (r/type! :enter)
  (sleep 7000)

  ; autoriser les cookies
  (click 732 674)
  (sleep 1000)

  ; login
  ; aller à deposer une annonce
  (click 398 84)
  (paste "https://leboncoin.fr/deposer-une-annonce")
  (r/type! :enter)
  (sleep 3000)
  ; icone se connecter
  (click 1130 126)
  (sleep 5000)

  ; email
  (click 495 454)
  (sleep 500)
  (paste email)
  (sleep 500)
  ; password
  (click 542 548)
  (sleep 500)
  (paste password)
  (sleep 500)

  ; bouton se connecter
  (click 619 668)
  (sleep 7000)

  ; j'ai compris
  (click 605 644)
  (sleep 2000)
)

;(init)


(defn deposer-annonce-pro-mac [annonce]
  ; deposer annonce url
  (click 179 87)
  (sleep 500)
  (paste "leboncoin.fr/deposer-une-annonce")
  (r/type! :enter)
  (sleep 3000)

  ; prestation de services
  ; categories 
  ; services > prestation de services
  ; search bar
  (click 314 334)
  (sleep 200)
  (paste "prestation")
  (sleep 1000)
  (r/type! :enter)
  (sleep 1000)
  ;continue
  (click 840 400)

  ; title
  (click 159 295)
  (click 159 295)
  (sleep 200)
  (paste (annonce :title))

  ;; description
  (click 147 517)
  ; (paste "description description")
  (paste (annonce :description))
  ; unfocus 
  (click 62 424)
  ; continue
  (r/hot-keys! [:cmd :down])
  (sleep 1000)
  (click 846 726)
  (sleep 1000)

  ; price
  ; no price

  ; continue
  (click 825 367)
  (sleep 1000)

  ;; image
  ; load image button
  (click 198 303)
  (sleep 1000)


  ; click Telechargements
  (click 337 288)
  (sleep 200)

  ; search bar
  (click 873 116)
  (sleep 500)
  (paste (annonce :image))
  (sleep 1000)
  (r/type! :enter)
  (sleep 2000)
  (click 476 253)
  (sleep 500)
  (r/type! :enter)

  ; wait image loading
  (sleep 4000)
  ; continue
  (click 826 642)
  (sleep 1000)

  ; location
  (triple-click 661 273)
  (sleep 1000)
  (paste (annonce :location))
  (sleep 1000)
  (r/type! :enter)
  (sleep 1000)
  (r/type! :enter)
  (sleep 2000)
  ; ;unfocus
  ; (click 70 397)
  ; (sleep 500)
  ; ; continue
  ; (click 877 558)
  ; (sleep 1000)


  ; phone
  (double-click 614 514)
  (sleep 500)
  (paste (annonce :phone))
  (sleep 1000)

  ; ;;;;;;;;;;;;;;;;;;;;; 
  ; continue
  (click 848 669)
  (sleep 3000)
  ; no boost
  (click 995 775)
  (sleep 4000)

  ; back to homepage
  (click 174 131)
  (sleep 3000)

  ; idle position, tells the bot finished
  (r/mouse-move! 100 100))

; (deposer-annonce-pro-mac (annonce 2))


; ((annonce 1) :image)


(def timing 
  (* 1000 (int (cell "G5"))))


(defn deposer-10-annonces-pro []
  (deposer-annonce-pro-mac (annonce 1))
  (sleep timing)
  (deposer-annonce-pro-mac (annonce 2))
  (sleep timing)
  (deposer-annonce-pro-mac (annonce 3))
  (sleep timing)
  (deposer-annonce-pro-mac (annonce 4))
  (sleep timing)
  (deposer-annonce-pro-mac (annonce 5))
  (sleep timing)
  (deposer-annonce-pro-mac (annonce 6))
  (sleep timing)
  (deposer-annonce-pro-mac (annonce 7))
  (sleep timing)
  (deposer-annonce-pro-mac (annonce 8))
  (sleep timing)
  (deposer-annonce-pro-mac (annonce 9))
  (sleep timing)
  (deposer-annonce-pro-mac (annonce 10)))

(defn run-bot []
  (init)
  (deposer-10-annonces-pro))


(defn -main
  "runs the bot"
  [& args]
  (println "bot started")#!/usr/bin/env clojure
  (run-bot))

; (-main)


;       ; ... do your work


; (deposer-annonce-pro-mac (annonce 10))

;; start stop
; (defn f-deposer-annonce-pro [n]v
;   (while true
;     (if-not (Thread/interrupted)
;       ; ... do your work
;       (deposer-annonce-pro (annonce n))
;       ; ... ------------
;       (throw (InterruptedException. "Function interrupted...")))))

; (defn t-annonce [n] 
;   (Thread. (fn []
;             (try
;               (while true
;                 (f-deposer-annonce-pro n))
;               (catch InterruptedException e
;                 (println (.getMessage e)))))))


; (defn f-deposer-10-annonces-pro []
;   (while true
;     (if-not (Thread/interrupted)

;       ; ... do your work
;       (deposer-10-annonces-pro)
;       ; ... ------------
;       (throw (InterruptedException. "Function interrupted...")))))

; (def t-10-annonces
;   (Thread. (fn []
;             (try
;               (while true
;                 (f-deposer-10-annonces-pro))
;               (catch InterruptedException e
;                 (println (.getMessage e)))))))

; (def t-annonce-1
;   (t-annonce 1))

; (.start t-annonce-1)
; (.interrupt t-annonce-1)


;; (.start t-10-annonces)
;; (.interrupt t-10-annonces)





; (defn -main
;   "I don't do a whole lot ... yet."
;   [& args]
;   (println "Hello, World!")
;   (deposer-10-annonces-pro))



;(r/mouse-pos)

;; excel => v
;; 10 => v
;; stop the bot => v
