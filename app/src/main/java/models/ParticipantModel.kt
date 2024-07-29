package models

class ParticipantModel {

    private var name : String = ""
    private var email : String = ""
    private var phone_no : Int = 0
    private var promocode : String = ""
    private var quantity : Int = 0

     fun setName(name : String){
        this.name = name
    }

     fun getName() : String{
        return this.name
    }

     fun setEmail(email : String){
        this.email = email
    }

     fun getEmail() : String{
        return this.email
    }

     fun setPhoneNo(phoneno : Int){
        this.phone_no = phoneno
    }

     fun getPhoneNo() : Int{
        return this.phone_no
    }

     fun setPromoCode(promocode : String){
        this.promocode = promocode
    }

     fun getPromoCode() : String{
        return this.promocode
    }

     fun setQuantity(quantity : Int){
        this.quantity = quantity
    }

     fun getQuantity() : Int{
        return this.quantity
    }




}