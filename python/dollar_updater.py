from selenium import webdriver as wd
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time, datetime

url = 'https://www.cambiostore.com/dolar-canadense'

print ('Start processing with url ', url)
print ('Getting Web driver')
driver = wd.Chrome('/home/coelhocaique/Documents/chromedriver')
print ('Done.')

while True:
    print ('Navigating to page')
    driver.get(url);
    print ('Sleeping ...')
    time.sleep(10)
    print ('Getting element ...')
    dolar_price = driver.find_element_by_class_name('simulator-content__price-description').text
    print (datetime.datetime.now().time())
    print (dolar_price)
    time.sleep(50)
