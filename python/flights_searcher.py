from selenium import webdriver as wd
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time, datetime

print ('Enter a name for decolar.com url ')
search_name = str(input())
print ('Enter the url')
url = str(input())

#montreal_flights = 'https://www.decolar.com/shop/flights/results/multipledestinations/SAO,NYC/YMQ,GRU/2018-03-31,2018-04-30/1/0/0?from=SB'

#toronto_flights = 'https://www.decolar.com/shop/flights/results/multipledestinations/SAO,NYC/YTO,GRU/2018-03-31,2018-04-30/1/0/0?from=SB'
print ('Start processing ', search_name, ' with url ', url)

print ('Getting Web driver')
driver = wd.Chrome('/home/coelhocaique/Documents/chromedriver')
print ('Done.')

while True:
    print ('\nNavigating to page,  ', datetime.datetime.now().time())
    driver.get(url);
    print ('Sleeping ...')
    time.sleep(10)
    print ('Getting elements ...')
    elements = driver.find_elements(By.TAG_NAME, 'airlines-matrix-airline')
    print ('Done. ', len(elements), ' elements found')
    prices_map = dict()

    for e in elements:
        airline = e.find_element_by_class_name('airline-name').text
        price_e = e.find_elements_by_tag_name('em')
        current_price = price_e[0].text

        if airline in prices_map:
            last_price = prices_map.get(airline)
            msg = '\nThere is a change in ' + airline + ' flight price. It seems to be '

            if current_price < last_price:
                msg += 'droppping.'
            else:
                msg += 'rising.'

            print (msg)

        prices_map[airline] = current_price
    for key in prices_map.keys():
        print(key,': R$',prices_map.get(key))

    time.sleep(600)
