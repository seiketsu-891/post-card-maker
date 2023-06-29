<template>
  <div class="my-postcards">
    <!-- 明信片项目展示区 -->
    <div class="scrollable">
      <div class="postcards__container">
        <a class="postcards__wrapper" v-for="card in projects" :key="card.id">
          <img :src="card.snapshot" class="postcards__img" />
          <span class="postcards__name">{{ card.name }}</span>
        </a>
        <InfiniteLoading @infinite="getUserPostCards" :key="loadingKey">
          <template #complete> <span></span> </template>
        </InfiniteLoading>
      </div>
    </div>
  </div>
</template>
<script>
import { getProjects } from "@/service/postcard";
export default {
  data() {
    return {
      projects: [],
      loadingKey: 0,
      pageNum: 1,
    };
  },
  methods: {
    /**
     * 获取明信片
     */
    async getUserPostCards($state) {
      const res = await getProjects(this.pageNum, 5);
      if (res.code == 200) {
        if (res.data.list.length < 5) {
          $state.complete();
        }
        this.projects.push(...res.data.list);
      }
      this.pageNum++;
    },
  },
};
</script>
<style lang="sass" scoped>
.my-postcards
  height: 100%
.scrollable
   height: 100%
   overflow-y: auto
.postcards
  &__container
      width: 100%
      text-align: center
      padding: 20px
      padding-bottom: 0
  &__wrapper
      display: block
      padding: 10px
      margin-bottom: 30px
      box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px
      &:hover
        box-shadow: rgba(99, 99, 99, 0.3) 0px 2px 8px 0px
  &__img
      display: block
      width: 140px
      margin: 0 auto
  &__name
      color: #4a4a4a
</style>
