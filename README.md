# Amazing
This project tries to immitate how a marketstore like Amazon would work. You can:
-Buy products distributed inside categories.
-Compare products.
-Create an account that holds all your orders.
-Change the password of the account.
-Change the regions.
-Get currencies conversion rate from the net.
-Create locales with a predefined template.
-As an admin you can add products, categories and promote other users to admin.
-All the values are saved inside files (passwords are encrypted).

The project is covered with EclEmma and optimized with SonarLint, if you find anything that could be optimized, please create a pull request and we'll merge it as soon as we verify it.

The converter uses a [website](http://currencies.apps.grandtrunk.net/) made by Wim Heirman, thanks to him the converter rate gets the latest value from the net.

## How to manually modify settings
In case you want to manually create things outside of the app, here is the template for the files.

Note that you shouldn't modify the password and that you can't see it in plain text. In case you want the password you could decrypt it.

data_path // You can use this file to change the data location
```
DATA_PATH=(Path)
```

d_user //User data - All the user data is loaded here
```
u_email=(String)
u_password=(Encrypted string)
u_login=(Long)
u_last_login=(Long)
u_admin=(Int)
```

d_category //Category - Categories will be stored here.
```
category=(String)
```

d_product //Product - Product data will be here
```
p_id=(Int)
p_name=(String)
p_category=(String)
p_price=(Float)
p_stock=(Int)
```

d_product_user //Product user - The data from the orders
```
pu_u_id=(String)
pu_p_id=(Int)
pu_p_name=(String)
pu_p_price=(Float)
pu_number=(Int)
```

d_converter_rate //Converter rate - Currency rate from and to
```
(From currency)/(To currency)
conv_date=(Long)
conv_rate=(Float)
conv_symb=(String)
```

d_region //Region - Data for the region and it's currency
```
(Region Name)
r_currency=(String)
```

d_locale //Locale - Data for the locale and it's language
```
(Country name)
l_lang=(String)
```

## To do list:

☒ Threads