package models

class PopularEventModel {

    private var eventid: Int? = null
    private var eventname: String = ""
    private var eventtype: String = ""
//    private var eventstarttime: String = ""
//    private var eventfinishtime: String = ""

    // Setter methods
    fun setEventId(id: Int?) {
        this.eventid = id
    }

    fun setEventName(name: String) {
        this.eventname = name
    }

    fun setEventType(type: String) {
        this.eventtype = type
    }

//    fun setEventStartTime(startTime: String) {
//        this.eventstarttime = startTime
//    }
//
//    fun setEventFinishTime(finishTime: String) {
//        this.eventfinishtime = finishTime
//    }

    // Getter methods
    fun getEventId(): Int? {
        return this.eventid
    }

    fun getEventName(): String {
        return this.eventname
    }

    fun getEventType(): String {
        return this.eventtype
    }

//    fun getEventStartTime(): String {
//        return this.eventstarttime
//    }
//
//    fun getEventFinishTime(): String {
//        return this.eventfinishtime
//    }


}