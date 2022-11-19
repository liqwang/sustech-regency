<template>
  <el-card class="box-card" style="border-radius: 15px">
    <b style="font-size: 25px">南科大专家公寓</b>
    <br />
    <div>
      <el-icon :size="20">
        <Location />
      </el-icon>
      广东省，深圳市，南山区
    </div>
    <br />
    <el-row>
      <el-col :span="12">
        <div class="block text-center">
          <el-carousel height="500px">
            <el-carousel-item v-for="url in urls" :key="url">
              <h3 class="small justify-center" text="2xl">
                <img :src="url" />
              </h3>
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-col>
      <el-col :span="10" :offset="1">
        <div m="t-10">
          <div>
            <el-rate v-model="rate" disabled show-score text-color="#ff9900" score-template="{value}分/5分" />
            <br />
            <b style="font-size: 20px">￥8888</b>
            起
          </div>
          <div>8888条评论</div>
          <br />
          <br />
          <article>
            桔子水晶是华住集团针对中高端市场设计师的品牌店，区别于传统酒店，桔子水晶每一家酒店都当作艺术品来打造，即便同一座城市，不同酒店也有不同的风格，桔子水晶陆家嘴世博园酒店，以"大道至简，上善若水"的设计理念，酒店位于浦建路靠近浦东南路，属于上海市陆家嘴金融贸易核心区域，地铁4号线4号口即可到达，距离仁济医院，儿童医学中心，黄浦江，步行仅约5分钟，，距新国际博览中心，梅赛德斯奔驰中心，世博园，陆家嘴金融中心，东方明珠，只需要10-15分钟路程，酒店附近有世纪公园，金茂大厦，环球金融中心，上海中心大厦，上海科技馆，中华艺术馆，滨江大道，上海水族馆
            。
          </article>
        </div>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup lang="ts">
import { HomeFilled, House, MapLocation, Location } from '@element-plus/icons-vue'
const urls = [
  'https://z1.muscache.cn/im/pictures/642803/e0a0ddc9_original.jpg?aki_policy=large',
  'https://z1.muscache.cn/im/pictures/miso/Hosting-45337054/original/d0e063e3-f760-4160-8f56-ec1dcc7d7392.jpeg?aki_policy=large',
  'https://z1.muscache.cn/im/pictures/c7e7c673-9673-4d08-be5a-ce7c2c7143dd.jpg?aki_policy=large'
]
const rate = $ref(5.0)
</script>

<style scoped></style>
