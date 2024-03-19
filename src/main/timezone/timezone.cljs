(ns timezone.timezone
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

(defn init []
  (println "i have inited"))

(defn timezone-app []
  [:h1 "i am a div"])

(rd/render [timezone-app] (js/document.getElementById "app"))


