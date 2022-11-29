export class HotelInfo {
    id: number
    latitude: number
    longitude: number
    name: string
    tel: string
    address: string
    provinceName: string
    cityName: string
    regionName: string
    stars: number
    coverUrl: string
    videoUrls: string[]
    pictureUrls: string[]
    minPrice: number
    commentNum: number
    likes_num: number
    description: string
}

export interface Order {
    id: string
    roomId: number
    dateStart: string
    dateEnd: string
    payerId: number
    fee: number
    status: string
    comment: string
    commentTime: string
    stars: number
    payTime: string
    createTime: string
    refundTime: string
}

export interface RoomInfo {
    id: number
    roomNum: number
    price: number
    floor: number
    typeId: number
    hotelId: number
    isAvailable: boolean
    discount: number
    roomTypeName: string
    capacity: number
    toiletNum: number
    hasLivingRoom: boolean
    coverUrl: string
    videoUrls: string[]
    pictureUrls: string[]
}

export interface OrderInfo {
    order: Order
    hotelInfo: HotelInfo
    roomInfo: RoomInfo
}