# Amazing
This project tries to immitate how a marketstore like Amazon would work. It gives you the option to create products, category (only if you are an admin) and buy the products.

This project uses a [website](http://currencies.apps.grandtrunk.net/) made by Wim Heirman, thanks to him the converter rate gets the latest value from the net.

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

d_conversor_rate //Conversor rate - Currency rate from and to
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

## To do list:
☒ Give admin to a user without the need to access the files (having a default admin).

☒ Allow to change the password.

☒ Delete categories and products (will have to update the product user to retain data).

☒ Localize.
