package com.rit.tcs.bussinessobject;

/**
 * Created by SONU on 25/09/15.
 */
public class AbstractDataModel {

    // Getter and Setter model for recycler view items
    private String title;
    private int image;
    private int selectedImage;
    private String price;
    private boolean selected;

    public AbstractDataModel(String title, int image, int selectedImage, String price) {
        this.title = title;
        this.image = image;
        this.selectedImage = selectedImage;
        this.price = price;
        this.selected = false;
    }

    public String getTitle() {
        return title;
    }



    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public int getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(int selectedImage) {
        this.selectedImage = selectedImage;
    }
}


/*
* {
    "status": "success",
    "message": "Here is your details",
    "profile": [
        {
            "name": "Kuntal Chakraborty",
            "email": "kuntalchakraborty@gmail.com",
            "phone": "9874467936"
        }
    ],
    "myassets": [
        {
            "id": 12,
            "name": "HP Elite Book",
            "serialnumber": "1276309002",
            "category_id": 10,
            "category_name": "LAPTOP",
            "locker_number": "B827EBB22E4F_1"
        },
        {
            "id": 6,
            "name": "Iphone X",
            "serialnumber": "FVFTPNH4HGBH",
            "category_id": 1,
            "category_name": "Iphone",
            "locker_number": "B827EBB22E4F_5"
        }
    ],
    "allassets": [
        {
            "catid": 20,
            "cat_name": "Wireless Landline Telephone",
            "assets": [
                {
                    "id": 31,
                    "name": "Panasonics",
                    "serial_number": "PLL12345",
                    "locker_number": "B827EBB22E4F_10"
                }
            ]
        },
        {
            "catid": 19,
            "cat_name": "Laptop Charger",
            "assets": [
                {
                    "id": 31,
                    "name": "Panasonics",
                    "serial_number": "PLL12345",
                    "locker_number": "B827EBB22E4F_10"
                },
                {
                    "id": 30,
                    "name": "HP",
                    "serial_number": "WDWRT0AAR8JPH",
                    "locker_number": "B827EBB22E4F_9"
                }
            ]
        },
        {
            "catid": 18,
            "cat_name": "Digital Multimeter",
            "assets": [
                {
                    "id": 31,
                    "name": "Panasonics",
                    "serial_number": "PLL12345",
                    "locker_number": "B827EBB22E4F_10"
                },
                {
                    "id": 30,
                    "name": "HP",
                    "serial_number": "WDWRT0AAR8JPH",
                    "locker_number": "B827EBB22E4F_9"
                },
                {
                    "id": 29,
                    "name": "HTC DM-97",
                    "serial_number": "1245358",
                    "locker_number": "B827EBB22E4F_8"
                }
            ]
        },
        {
            "catid": 17,
            "cat_name": "Beacon",
            "assets": [
                {
                    "id": 31,
                    "name": "Panasonics",
                    "serial_number": "PLL12345",
                    "locker_number": "B827EBB22E4F_10"
                },
                {
                    "id": 30,
                    "name": "HP",
                    "serial_number": "WDWRT0AAR8JPH",
                    "locker_number": "B827EBB22E4F_9"
                },
                {
                    "id": 29,
                    "name": "HTC DM-97",
                    "serial_number": "1245358",
                    "locker_number": "B827EBB22E4F_8"
                },
                {
                    "id": 28,
                    "name": "Aruba",
                    "serial_number": "20C38FF1881D",
                    "locker_number": "B827EBB22E4F_7"
                }
            ]
        },
        {
            "catid": 16,
            "cat_name": "Tab",
            "assets": [
                {
                    "id": 31,
                    "name": "Panasonics",
                    "serial_number": "PLL12345",
                    "locker_number": "B827EBB22E4F_10"
                },
                {
                    "id": 30,
                    "name": "HP",
                    "serial_number": "WDWRT0AAR8JPH",
                    "locker_number": "B827EBB22E4F_9"
                },
                {
                    "id": 29,
                    "name": "HTC DM-97",
                    "serial_number": "1245358",
                    "locker_number": "B827EBB22E4F_8"
                },
                {
                    "id": 28,
                    "name": "Aruba",
                    "serial_number": "20C38FF1881D",
                    "locker_number": "B827EBB22E4F_7"
                },
                {
                    "id": 27,
                    "name": "Lenovo",
                    "serial_number": "HGCGXFYV(73)",
                    "locker_number": "B827EBB22E4F_6"
                }
            ]
        },
        {
            "catid": 15,
            "cat_name": "Keyboard",
            "assets": [
                {
                    "id": 31,
                    "name": "Panasonics",
                    "serial_number": "PLL12345",
                    "locker_number": "B827EBB22E4F_10"
                },
                {
                    "id": 30,
                    "name": "HP",
                    "serial_number": "WDWRT0AAR8JPH",
                    "locker_number": "B827EBB22E4F_9"
                },
                {
                    "id": 29,
                    "name": "HTC DM-97",
                    "serial_number": "1245358",
                    "locker_number": "B827EBB22E4F_8"
                },
                {
                    "id": 28,
                    "name": "Aruba",
                    "serial_number": "20C38FF1881D",
                    "locker_number": "B827EBB22E4F_7"
                },
                {
                    "id": 27,
                    "name": "Lenovo",
                    "serial_number": "HGCGXFYV(73)",
                    "locker_number": "B827EBB22E4F_6"
                },
                {
                    "id": 26,
                    "name": "Marcury",
                    "serial_number": "KB3780U180802",
                    "locker_number": "B827EBB22E4F_5"
                }
            ]
        },
        {
            "catid": 14,
            "cat_name": "Monitor",
            "assets": [
                {
                    "id": 31,
                    "name": "Panasonics",
                    "serial_number": "PLL12345",
                    "locker_number": "B827EBB22E4F_10"
                },
                {
                    "id": 30,
                    "name": "HP",
                    "serial_number": "WDWRT0AAR8JPH",
                    "locker_number": "B827EBB22E4F_9"
                },
                {
                    "id": 29,
                    "name": "HTC DM-97",
                    "serial_number": "1245358",
                    "locker_number": "B827EBB22E4F_8"
                },
                {
                    "id": 28,
                    "name": "Aruba",
                    "serial_number": "20C38FF1881D",
                    "locker_number": "B827EBB22E4F_7"
                },
                {
                    "id": 27,
                    "name": "Lenovo",
                    "serial_number": "HGCGXFYV(73)",
                    "locker_number": "B827EBB22E4F_6"
                },
                {
                    "id": 26,
                    "name": "Marcury",
                    "serial_number": "KB3780U180802",
                    "locker_number": "B827EBB22E4F_5"
                },
                {
                    "id": 23,
                    "name": "Zebronics",
                    "serial_number": "ZEBAU16FHDLED",
                    "locker_number": "B827EBB22E4F_2"
                }
            ]
        },
        {
            "catid": 13,
            "cat_name": "Category Name",
            "assets": []
        },
        {
            "catid": 11,
            "cat_name": "Tablet",
            "assets": []
        },
        {
            "catid": 10,
            "cat_name": "LAPTOP",
            "assets": [
                {
                    "id": 25,
                    "name": "HCL ME",
                    "serial_number": "6104AE485913",
                    "locker_number": "B827EBB22E4F_4"
                },
                {
                    "id": 24,
                    "name": "Apple Macbook",
                    "serial_number": "4540",
                    "locker_number": "B827EBB22E4F_3"
                },
                {
                    "id": 5,
                    "name": "LAPTOP 4GB",
                    "serial_number": "FVFTPNH4H3GB",
                    "locker_number": "B827EBB22E4F_4"
                },
                {
                    "id": 1,
                    "name": "MACBOOK",
                    "serial_number": "FVFTPNH4H3QD",
                    "locker_number": "B827EBB22E4F_3"
                }
            ]
        },
        {
            "catid": 9,
            "cat_name": "Ios Tablet",
            "assets": []
        },
        {
            "catid": 8,
            "cat_name": "Android Tablet",
            "assets": []
        },
        {
            "catid": 1,
            "cat_name": "Iphone",
            "assets": []
        }
    ],
    "secretkey": "3409df7579e48e3671a5cff3084be507"
}
* */