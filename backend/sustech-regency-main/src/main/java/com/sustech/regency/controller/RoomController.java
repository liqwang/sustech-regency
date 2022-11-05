package com.sustech.regency.controller;

import com.sustech.regency.db.po.Room;
import com.sustech.regency.service.RoomService;
import com.sustech.regency.web.annotation.PathController;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import static com.sustech.regency.util.VerificationUtil.getUserId;

@PathController("/room")
public class RoomController {
    @Resource
    private RoomService roomService;

    @ApiOperation("添加一个房间")
    @PostMapping("/room/add")
    public ApiResponse<Boolean> addNewRoom(@ApiParam(value = "房间号", required = true) @RequestParam @NotNull Integer roomNum,
                                           @ApiParam(value = "价格", required = true) @RequestParam @NotNull Float price,
                                           @ApiParam(value = "楼层", required = true) @RequestParam @NotNull Integer floor,
                                           @ApiParam(value = "房间类型", required = true) @RequestParam @NotNull Integer typeId,
                                           @ApiParam(value = "属于哪个酒店", required = true) @RequestParam @NotNull Integer hotelId,
                                           @ApiParam(value = "是否空置中", required = true) @RequestParam @NotNull Boolean isAvailable,
                                           @ApiParam(value = "折扣率", required = true) @RequestParam @NotNull Float discount) {
        Room room = Room.builder().roomNum(roomNum)
                .price(price)
                .floor(floor)
                .typeId(typeId)
                .hotelId(hotelId)
                .isAvailable(isAvailable)
                .discount(discount)
                .build();
        return ApiResponse.success(roomService.addNewRoom(room));
    }

    @ApiOperation("商家删除一个酒店房间")
    @PostMapping("/room/delete")
    public ApiResponse<Boolean> deleteRoom(@ApiParam(value = "房间Id", required = true) @RequestParam @NotNull Integer roomId) {
        return ApiResponse.success(roomService.deleteRoom(getUserId(), roomId));
    }

    @ApiOperation("商家更新一个酒店房间信息")
    @PostMapping("/room/updateOne")
    public ApiResponse<Boolean> updateOneRoom(@ApiParam(value = "房间Id", required = true) @RequestParam @NotNull Integer roomId,
                                              @ApiParam(value = "房间号") @RequestParam(required = false) Integer roomNum,
                                              @ApiParam(value = "价格") @RequestParam(required = false) Float price,
                                              @ApiParam(value = "楼层") @RequestParam(required = false) Integer floor,
                                              @ApiParam(value = "房间类型") @RequestParam(required = false) Integer typeId,
                                              @ApiParam(value = "属于哪个酒店") @RequestParam(required = false) Integer hotelId,
                                              @ApiParam(value = "是否空置中") @RequestParam(required = false) Boolean isAvailable,
                                              @ApiParam(value = "折扣率") @RequestParam(required = false) Float discount) {
        return ApiResponse.success(roomService.updateOneRoom(getUserId(), roomId, roomNum, price, floor, typeId, hotelId, isAvailable, discount));
    }

    @ApiOperation("商家更新一些房间信息")
    @PostMapping("/room/updateRooms")
    public ApiResponse<Boolean> updateOneRooms(@ApiParam(value = "价格") @RequestParam(required = false) Float price,
                                               @ApiParam(value = "房间类型") @RequestParam(required = false) Integer typeId,
                                               @ApiParam(value = "属于哪个酒店") @RequestParam(required = false) Integer hotelId,
                                               @ApiParam(value = "折扣率") @RequestParam(required = false) Float discount) {
        return ApiResponse.success(roomService.updateRooms(getUserId(), hotelId, typeId, price, discount));
    }
}
