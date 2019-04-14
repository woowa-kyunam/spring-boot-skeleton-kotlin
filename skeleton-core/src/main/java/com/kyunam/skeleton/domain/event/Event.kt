package com.kyunam.skeleton.domain.event

import com.kyunam.skeleton.common.BaseIdEntity
import com.kyunam.skeleton.common.enum.EventStatus
import com.kyunam.skeleton.common.exception.EventValidationException
import com.kyunam.skeleton.common.exception.UnAuthorizationException
import com.kyunam.skeleton.domain.account.Account
import com.kyunam.skeleton.dto.event.EventDto
import org.springframework.util.ObjectUtils
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "events", indexes = [Index(columnList = "events_name", name = "IDX_EVENT_NAME")])
class Event(name: String?,
            contents: String?,
            address: Address?,
            price: Int?,
            availableParticipant: Int,
            register: Account?,
            beginEnrollmentDateTime: LocalDateTime,
            endEnrollmentDateTime: LocalDateTime,
            beginEventDateTime: LocalDateTime,
            endEventDateTime: LocalDateTime
) : BaseIdEntity() {

    @Column(name = "events_name", nullable = false)
    var name: String? = null
        set (name) {
            if (!name.isNullOrEmpty()) field = name else throw EventValidationException("이벤트 이름은 필수 정보입니다.")
        }
    @Lob
    @Column(name = "events_contents", nullable = false)
    var contents: String? = null
        set(contents) {
            if (!contents.isNullOrEmpty()) field = contents else throw EventValidationException("이벤트 내용은 필수 정보입니다.")
        }
    @AttributeOverrides(
            AttributeOverride(name = "localAddress", column = Column(name = "event_local_address")),
            AttributeOverride(name = "roadAddress", column = Column(name = "event_road_address")),
            AttributeOverride(name = "postalCode", column = Column(name = "event_postal_code"))
    )
    var address: Address? = null
        set(address) {
            if (address != null && address.isEmpty()) {
                throw EventValidationException("이벤트 주소는 필수적으로 입력되어야 합니다.")
            }
            field = address
        }
    @Column(name = "events_price", nullable = false)
    var price: Int? = null
        set(price) {
            if (!attendances.isEmpty()) {
                throw EventValidationException("참석인원이 존재해 가격 수정이 불가능합니다.")
            }
            if (price == null || price <= MIN_PRICE || price > MAX_PRICE) {
                throw EventValidationException("이벤트 가격은 0~100만원 사이로 설정해야 합니다.")
            }
            field = price
        }
    @Column(name = "events_available_participant", nullable = false)
    var availableParticipant: Int = 0
        set(availableParticipant) {
            if (availableParticipant < MIN_PARTICIPANT || availableParticipant > MAX_PARTICIPANT) {
                throw EventValidationException("이벤트 참석자 가능인원은 0~1000명 사이로만 설정해야합니다.")
            }
            if (this.attendances.size > availableParticipant) {
                throw EventValidationException("참석인원보다 참석가능인원을 작게 설정할 수 없습니다.")
            }
            field = availableParticipant
        }
    var beginEnrollmentDateTime: LocalDateTime = LocalDateTime.MIN
        set (beginEnrollmentDateTime) {
            if (beginEnrollmentDateTime.isBefore(LocalDateTime.now())) {
                throw EventValidationException("등록 시작시간을 과거시간으로 등록할 수 없습니다.")
            }
            field = beginEnrollmentDateTime
        }
    var endEnrollmentDateTime: LocalDateTime = LocalDateTime.MIN
        set (endEnrollmentDateTime) {
            if (endEnrollmentDateTime.isBefore(LocalDateTime.now())) {
                throw EventValidationException("등록 마감시간을 과거시간으로 등록할 수 없습니다.")
            }

            if (endEnrollmentDateTime.isBefore(this.beginEnrollmentDateTime)) {
                throw EventValidationException("등록 마감시간을 등록시작시간보다 과거시간으로 등록할 수 없습니다.")
            }
            field = endEnrollmentDateTime
        }
    var beginEventDateTime: LocalDateTime = LocalDateTime.MIN
        set (beginEventDateTime) {
            if (beginEventDateTime.isBefore(LocalDateTime.now())) {
                throw EventValidationException("이벤트 시작시간을 과거시간으로 등록할 수 없습니다.")
            }

            if (beginEventDateTime.isBefore(endEnrollmentDateTime)) {
                throw EventValidationException("이벤트 시작시간을 이벤트 등록 마감시간 이전으로 등록할 수 없습니다.")
            }

            val maxBeginEventDateTime = beginEnrollmentDateTime.plusMonths(1)
            if (beginEventDateTime.isAfter(maxBeginEventDateTime)) {
                throw EventValidationException("이벤트 시작시간을 이벤트 등록 시작시간 한달 이내로 설정 해야합니다.")
            }
            field = beginEventDateTime
        }
    var endEventDateTime: LocalDateTime = LocalDateTime.MIN
        set (endEventDateTime) {
            if (endEventDateTime.isBefore(LocalDateTime.now())) {
                throw EventValidationException("이벤트 종료시간을 과거시간으로 등록할 수 없습니다.")
            }

            if (endEventDateTime.isBefore(beginEventDateTime)) {
                throw EventValidationException("이벤트 종료시간을 이벤트 시작시간보다 과거시간으로 등록할 수 없습니다.")
            }

            field = endEventDateTime
        }
    @OneToMany(mappedBy = "attendEvent")
    var attendances: MutableSet<Attendance> = mutableSetOf()
        private set
    @OneToOne
    var register: Account? = null
        set (register) {
            if (ObjectUtils.isEmpty(register)) {
                throw EventValidationException("이벤트 등록자는 필수 항목입니다.")
            }
            field = register
        }

    @Enumerated(EnumType.STRING)
    var eventStatus = EventStatus.DRAFT
        private set

    fun addAttendance(attendance: Attendance) {
        if (!isEnableAttend()) {
            throw EventValidationException("이벤트 등록 인원을 초과했습니다.")
        }
        attendance.attendEvent = this
        this.attendances.add(attendance)
    }

    fun updateEvent(account: Account, eventRequestDto: EventDto.EventRequestDto) {
        if (!isEventOwner(account)) {
            throw UnAuthorizationException("이벤트 등록자만 수정할 수 있습니다.")
        }

        if (!this.isBeforeOfAmendDeadLine()) {
            throw EventValidationException("이벤트는 시작 시간보다 최소 1주일 전에만 수정 가능합니다.")
        }

        this.address = eventRequestDto.address
        this.availableParticipant = eventRequestDto.availableParticipant
        this.beginEnrollmentDateTime = eventRequestDto.beginEnrollmentDateTime
        this.beginEventDateTime = eventRequestDto.beginEventDateTime
        this.contents = eventRequestDto.contents
        this.endEnrollmentDateTime = eventRequestDto.endEnrollmentDateTime
        this.endEventDateTime = eventRequestDto.endEventDateTime
        this.name = eventRequestDto.name
        this.price = eventRequestDto.price
    }

    private fun isBeforeOfAmendDeadLine(): Boolean {
        val deadLine = beginEventDateTime.minusDays(7)
        return !LocalDateTime.now().isAfter(deadLine)
    }

    private fun isEventOwner(account: Account): Boolean {
        return register == account
    }

    private fun isEnableAttend(): Boolean {
        return attendances.size < availableParticipant
    }

    companion object {
        const val MIN_PRICE = 0
        const val MAX_PRICE = 1000000
        const val MIN_PARTICIPANT = 0
        const val MAX_PARTICIPANT = 1000
    }

    init {
        this.name = name
        this.contents = contents
        this.address = address
        this.price = price
        this.register = register
        this.availableParticipant = availableParticipant
        this.beginEnrollmentDateTime = beginEnrollmentDateTime
        this.endEnrollmentDateTime = endEnrollmentDateTime
        this.beginEventDateTime = beginEventDateTime
        this.endEventDateTime = endEventDateTime
    }
}
