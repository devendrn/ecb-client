package devendrn.ecb.client.model

data class KtuAnnouncement(
    val title: String,
    val message: String = "",
    val date: String,
    val attachments: List<KtuAnnouncementAttachment>
)

data class KtuAnnouncementAttachment(
    val title: String,
    val name: String,
    val encryptId: String
)
