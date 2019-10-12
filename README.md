![icon](https://github.com/lwreaper/WhereMyBusAh/blob/master/app/src/main/res/mipmap-xhdpi/ic_launcher.png "Icon")

# WhereMyBusAh (v1.0.0)
__*Please note that everything inside this branch is for the upcoming iteration of WMBA, and it is not yet stable. It is for interested parties that likes testing out new features or just for debugging purposes*__
> For those Singaporeans always wanting to check bus arrivals!

# Class names and what it does:
- DisplayTiming
    -   Activity for displaying timing (Hidden, only accesible by intent and extras)
- FetchBusStopNames
    -   Async task to fetch all bus stop names
- FetchTimingsByCode
    -   Async task to get the timings by BUS STOP CODE and redirect setAdapter to ListView
- FetchTimingsByName
    -   Wrapper for FetchTimingsByCode (Accepts the bus stop name, check against a JSON and run FetchTimingsByCode using the return value of the bus stop name [which is the bus stop code])
- TimingAdapter
    -   Custom adapter for timing(s), used in FetchTimingsByCode
- WMBA
    -   MainActivity

# External APIs or JSON or Data-sets
-   BusService ([here](https://github.com/nabilcreates/BusService/))
    -   One-click update data-set using NodeJS. Read the documentation on how this works!
# Disclaimer
*Since this app is not production-ready (unless stated in a Release), It is not my fault if you use the app for daily use and is late for work or school, etc. because the app keeps crashing or whatever!*