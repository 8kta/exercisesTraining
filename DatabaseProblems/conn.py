import mysql.connector

from getpass import getpass
from mysql.connector import connect, Error

try:
    connection = connect(host="localhost", user='alonso', password='password',       				database="test_database")
	        
except Error as e:
    print(e)
    
    

