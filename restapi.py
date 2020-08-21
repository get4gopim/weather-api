#Python 3.8

import json
from collections import namedtuple
import requests

weatherResponse = requests.get('http://localhost:8080/forecast/weather')
print (weatherResponse)

goldRateResponse = requests.get('http://localhost:8080/forecast/gold')
print (goldRateResponse)

# Weather API sample reponse
"""
Weather:
  {
   "temperature":"33°",
   "low":"--",
   "high":"27°",
   "asOf":"15:33",
   "currentCondition":"Mostly Cloudy",
   "location":"Thalambur, Tamil Nadu"
 }
"""

# Gold Rate API sample response
"""
RateInfo:
  {
    "goldRate22":"5084",
    "goldRate24":null,
    "silver":"74.30"
  }
"""

if weatherResponse:
	weather_json = weatherResponse.text
	print (weather_json)

	weatherInfo = json.loads(weather_json, object_hook = lambda d: namedtuple('weatherInfo', d.keys()) (*d.values()))
	print ('Temp: ', weatherInfo.temperature, ' Date: ', weatherInfo.asOf)

else:
	print ('WeatherInfo API returned an error.')



if goldRateResponse:
	gold_json = goldRateResponse.text
	print (gold_json)

	rateInfo = json.loads(gold_json, object_hook = lambda d: namedtuple('rateInfo', d.keys()) (*d.values()))
	print ('Rate Card: ', rateInfo.goldRate22, ' Silver: ', rateInfo.silver)

else:
	print ('GoldRateInfo API Returned an error.')



# Ends Here.
