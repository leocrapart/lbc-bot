(ns clj-robot.core
  (:gen-class)
  (:require [robot.core :as r]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))



(r/mouse-move! 200 200)
(r/scroll! 1)
(r/mouse-pos)
(r/mouse-click!)

(defn click [x y]
  (sleep 100)
  (r/mouse-move! x y)
  (sleep 100)
  (r/mouse-click!)
  (sleep 100))

(defn paste [text]
  (sleep 100)
  (r/clipboard-put! text)
  (sleep 100)
  (r/hot-keys! [:ctrl :v])
  (sleep 100))

(defn sleep [ms]
  (Thread/sleep ms))

(def phone1 "0156374553")


(def description5
"🚚 Déménagement / Location de Camion avec Chauffeur  / Transport de Marchandises en Vrac ou sur Palettes

⚠️ Concernant la livraison nous n’effectuons pas les retraits en magasin ni les achats chez un particulier ⚠️

🔱 Nous vous proposons nos services de déménagement avec 2 formules :

🔑 Formule ECO : Camion + Chauffeur

🔑 Formule ZEN : Camion + Chauffeur qui aide au chargement et au déchargement 

🚚 Camions disponibles : 15m3 , 20m3 , 25m3 , 50m3 , 70 m3 , 100m3

Profitez de nos formules TOUT INCLUS 

🧾 DEVIS GRATUIT, précis et  sans SURPRISE

📞 Un renseignement ? Une question ? Appellez-nous ! 

✅ Matériels INCLUS et GRATUITS à disposition :

Sangles, Tapis antidérapants, Cales et Transpalette selon disponibilité


Exemples d’interventions régulières :

Location d’un utilitaire avec chauffeur pour déménagement 15m3 20m3 dans toute la france  / urgence petit ou gros déménagement décès, séparation ou succession 

Mots clés : camionnette fourgon déménageur camion chauffeur

🚚 Location de camion avec chauffeur  🚚

👨‍💻 DEVIS rapide , GRATUIT précis , sans SURPRISE


☎️  Besoin d’un renseignement ? N’hésitez surtout pas !  APPELEZ-NOUS !"
)

(def annonce5
{:title "Nous mobilisons une équipe de Déménageurs Qualifiés"
 :description description5
 :image "5.jpg"
 :location "Grenoble 38000"
 :phone phone1})

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

(defn deposer-annonce-pro [annonce]
  ; deposer annonce url
  (click 274 47)
  (sleep 500)
  (paste "leboncoin.fr/deposer-une-annonce")
  (r/type! :enter)
  (sleep 3000)

  ; prestation de services
  ; categories 
  ; services > prestation de services
  (click 400 800)
  (click 692 439)
  (sleep 1000)
  ; continue
  (click 1000 400)

  ; title
  (click 300 300)
  (paste (annonce :title))

  ;; description
  (click 313 513)
  ; (paste "description description")
  (paste (annonce :description))
  ; unfocus 
  (click 100 400)
  ; continue
  (r/scroll! 2)
  (sleep 1000)
  (click 1000 800)
  (sleep 1000)

  ; price
  ; no price

  ; continue
  (click 1000 370)
  (sleep 1000)

  ;; image
  ; load image button
  (click 380 340)
  (sleep 1000)
  ; path bar
  (click 460 50)
  (sleep 1000)
  ; path to image folder
  (paste "C:\\Users\\leocr\\Desktop\\lbc-images")
  (r/type! :enter)
  (sleep 1000)
  ; file name
  ; click file name input
  (click 310 414)
  ; insert file name
  (paste (annonce :image))
  (r/type! :enter)
  ; wait image loading
  (sleep 4000)
  ; continue
  (click 1000 640)
  (sleep 1000)

  ; wait image loading
  (sleep 4000)
  ; continue
  (click 1000 640)
  (sleep 1000)

  ; location
  (click 400 275)
  (click 400 275)
  (click 400 275)
  (paste (annonce :location))
  (r/type! :enter)
  (sleep 1000)
  ; continue
  (click 400 275)
  (r/type! :enter)
  (sleep 1000)

  ; phone
  (click 900 575)
  (click 900 575)
  (paste (annonce :phone))

  ; ;;;;;;;;;;;;;;;;;;;;; 
; ; ; continue
; ;   Click, 1000, 730
; ;   Sleep, 2000

; ; ; no boost
; ;   Click, 1200, 830
; ;   Sleep, 500

; ; ; back to homepage
; ;   Click, 330, 127
; ;   Sleep, 1000

  ; idle position, tells the bot finished
  (r/mouse-move! 100 100)
  )



(deposer-annonce-pro annonce5)


;; next : key to stop the bot 
;; clojure key to stop thread