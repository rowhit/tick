(ns tick.format
  "originally copied from https://github.com/dm3/clojure.java-time"
  (:refer-clojure :exclude (format))
  (:require
    [tick.interop :as t.i]
    #?@(:cljs
        [[java.time :refer [DateTimeFormatter]]]))
  #?(:clj
     (:import [java.time.temporal TemporalAccessor]
              [java.time.format DateTimeFormatter DateTimeFormatterBuilder ResolverStyle])))

(def predefined-formatters
  {:iso-zoned-date-time (t.i/static-prop  DateTimeFormatter ISO_ZONED_DATE_TIME)
   :iso-offset-date (t.i/static-prop  DateTimeFormatter ISO_OFFSET_DATE)
   :rfc-1123-date-time (t.i/static-prop  DateTimeFormatter RFC_1123_DATE_TIME)
   :iso-week-date (t.i/static-prop  DateTimeFormatter ISO_WEEK_DATE)
   :iso-offset-date-time (t.i/static-prop  DateTimeFormatter ISO_OFFSET_DATE_TIME)
   :iso-local-time (t.i/static-prop  DateTimeFormatter ISO_LOCAL_TIME)
   :iso-time (t.i/static-prop  DateTimeFormatter ISO_TIME)
   :iso-date (t.i/static-prop  DateTimeFormatter ISO_DATE)
   :basic-iso-date (t.i/static-prop  DateTimeFormatter BASIC_ISO_DATE)
   :iso-date-time (t.i/static-prop  DateTimeFormatter ISO_DATE_TIME)
   :iso-offset-time (t.i/static-prop  DateTimeFormatter ISO_OFFSET_TIME)
   :iso-local-date-time (t.i/static-prop  DateTimeFormatter ISO_LOCAL_DATE_TIME)
   :iso-local-date (t.i/static-prop  DateTimeFormatter ISO_LOCAL_DATE)
   :iso-ordinal-date (t.i/static-prop  DateTimeFormatter ISO_ORDINAL_DATE)
   :iso-instant (t.i/static-prop  DateTimeFormatter ISO_INSTANT)})

(defn ^DateTimeFormatter formatter
  "Constructs a DateTimeFormatter out of a

  * format string - \"YYYY/mm/DD\" \"YYY HH:MM\" etc.
  * formatter name - :date :iso-instant"
   [fmt]
  (let [^DateTimeFormatter fmt
        (cond (instance? DateTimeFormatter fmt) fmt
              (string? fmt) (. DateTimeFormatter ofPattern fmt)
              :else (get predefined-formatters fmt))]
    fmt))

(defn format
  "Formats the given time entity as a string.
  Accepts something that can be converted to a `DateTimeFormatter` as a first
  argument. Given one argument uses the default format."
  ([o] (str o))
  ([fmt o]
   (.format (formatter fmt) o)))