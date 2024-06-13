package com.example.myfirstapp_opsc7311

// Primary Constructor
class Order(){

    lateinit var productName: String
    lateinit var customerName: String
    lateinit var customerCell: String
    lateinit var orderDate: String

    //Secondary Constructor
    constructor(pName: String) : this()
    {
        productName = pName
    }

    //Secondary Constructor
    constructor(pName: String, cName: String, cCell: String, oDate: String) : this(pName)
    {
        customerName = cName
        customerCell = cCell
        orderDate = oDate
    }
}