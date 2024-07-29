package models

class MyTicketsModel {


    private var eventid: Int? = null
    private var eventname: String = ""
    private var eventtype: String = ""
    private var imgUri: String = ""
    private var eventstarttime: String = ""
    private var eventfinishtime: String = ""
    private var eventdescription: String = ""
    private var ticketno: Int = 0

    // Setter methods
    fun setTicketNo(ticketno : Int){

        this.ticketno = ticketno

    }

    fun setEventId(id: Int?) {
        this.eventid = id
    }

    fun setEventName(name: String) {
        this.eventname = name
    }

    fun setEventType(type: String) {
        this.eventtype = type
    }

//    fun setImageURI(uri: String){
//        this.imgUri = uri
//    }

    fun setEventStartTime(startTime: String) {
        this.eventstarttime = startTime
    }

    fun setEventFinishTime(finishTime: String) {
        this.eventfinishtime = finishTime
    }

    fun setEventDescription(eventdescription: String) {
        this.eventdescription = eventdescription
    }

    // Getter methods
//    fun getImageURI(): String {
//        return this.imgUri
//    }

    fun getEventId(): Int? {
        return this.eventid
    }

    fun getEventName(): String {
        return this.eventname
    }

    fun getEventType(): String {
        return this.eventtype
    }

    fun getEventStartTime(): String {
        return this.eventstarttime
    }

    fun getEventFinishTime(): String {
        return this.eventfinishtime
    }

    fun getEventDescription(): String {
        return this.eventdescription
    }

    // Setter methods
    fun getTicketNo() : Int{

        return this.ticketno

    }
}