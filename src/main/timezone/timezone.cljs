(ns timezone.timezone
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
            [tick.timezone]
            [tick.core :as t]
            [tick.locale-en-us]
            [cljc.java-time.zone-id :as zone-id]))

(def input-time (r/atom "12:00"))
(def all-timezones (zone-id/get-available-zone-ids))
(def input-timezone (r/atom "America/New_York"))
(def output-timezone (r/atom "America/Los_Angeles"))

(defn select-timezone [id timezone-atom]
  [:select {:id id
            :value @timezone-atom
            :on-change #(->> % .-target .-value (reset! timezone-atom))}
   (for [zone all-timezones]
     [:option {:value zone
               :key zone} zone])])

(defn timezone-app []
  [:div
   [:label {:for "timeinput"} "input time:"]
   [:input {:type "time"
            :id "timeinput"
            :value @input-time
            :on-change #(->> % .-target .-value (reset! input-time))}]
   
   [:label {:for "zone1"} " in timezone: "]
   (select-timezone "zone1" input-timezone)
   
   [:label {:for "zone2"} " to timezone: "]
   (select-timezone "zone2" output-timezone)
   
   (when (not-empty @input-time)
     (let [formatter (t/formatter "hh:mm a")
           input-time (-> (t/today)
                          (t/at (t/time @input-time))
                          (t/in @input-timezone))
           output-time (t/in input-time @output-timezone)
           input-formatted (t/format formatter input-time)
           output-formatted (t/format formatter output-time)]
       [:<>
        [:p "time is " input-formatted " in " @input-timezone]
        [:p "time is " output-formatted " in " @output-timezone]]))])

(defn init []
  (rd/render [timezone-app] (js/document.getElementById "app")))




