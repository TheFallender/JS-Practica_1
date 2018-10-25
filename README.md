# Amazing
This project tries to immitate how a marketstore like Amazon would work. It gives you the option to create products, category (only if you are an admin) and buy the products.

It also has an inbuilt Euro-Dollar converter.

If you want to be the admin, simply go to your user on the d_user file and change "u_admin=0" to "u_admin=1". Will implement a better and safer system in the future.


## Manually modify settings
In case you want to manually create things outside of the app, here is the template for the files.

data_path // You can use this file to change the data location

d_user //User data
u_email=
u_password=
u_login=
u_last_login=
u_admin=

d_category //Category data
category=

d_product //Product data
p_category=
p_id=
p_name=
p_price=
p_stock=

d_product_user //Product user data
pu_u_id=
pu_p_id=
pu_number=
