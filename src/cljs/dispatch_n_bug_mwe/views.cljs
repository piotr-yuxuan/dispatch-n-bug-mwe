(ns dispatch-n-bug-mwe.views
  (:require [re-frame.core :as re-frame]
            [dispatch-n-bug-mwe.events :as events]
            [dispatch-n-bug-mwe.subs :as subs]))

(def margin 5)

(defn- inline-pre
  [& children]
  [:pre {:style {:display :inline}}
   children])

(defn main-panel []
  (let [value (re-frame/subscribe [::subs/value])]
    [:div
     [:div
      [:button {:on-click #(do (re-frame/dispatch [::events/action]))} "Perform action on button"]
      [:span {:style {:margin 5}} "Value"]
      @value]
     [:div#question
      [:p "After reading the code of events, I'm quite convinced that the value should be added 2 each time you click the button:"]
      [:ul
       [:li "First event " [inline-pre "[::events/action-trigger 1]"] " gets " [inline-pre "0"] " as " [inline-pre "(:value db)"] " and adds " [inline-pre "1"] " to it through " [inline-pre "::events/action-db"] ", hence returning " [inline-pre "1"] " as new value in the app-db."]
       [:li "Second event " [inline-pre "[::events/action-trigger 1]"] " gets " [inline-pre "1"] " as " [inline-pre "(:value db)"] " and adds " [inline-pre "1"] " to it through " [inline-pre "::events/action-db"] ", hence returning " [inline-pre "2"] " as new value in the app-db."]]
      [:p "However, on Chrome version " [inline-pre "66.0.3335.0 (Official Build) canary (64-bit)"] " and with the dependencies versions listed in " [inline-pre "project.clj"] ", the actual value after a click is " [inline-pre "1"] ". Why?"]]]))
