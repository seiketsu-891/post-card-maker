<template>
  <div class="my-postcards">
    <div class="scrollable">
      <div class="postcards__container">
        <div>
          <div class="pics__empty" v-show="emptyState"><a-empty></a-empty></div>
          <a
            class="postcards__wrapper"
            v-for="card in projects"
            :key="card.id"
            @click="loadPostcard(card.id)"
          >
            <img :src="card.snapshot" class="postcards__img" />
            <span class="postcards__name">{{ card.name }}</span>
          </a>
        </div>
        <InfiniteLoading @infinite="getUserPostCards">
          <template #complete>
            <span></span>
          </template>
        </InfiniteLoading>
      </div>
    </div>
  </div>
</template>
<script>
import { getProjects, getPostcard } from "@/service/postcard";
import { message } from "ant-design-vue";
export default {
  data() {
    return {
      emptyState: false,
      projects: [],
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
        const postcards = res.data.list;
        if (res.data.total < 1) {
          this.emptyState = true;
          $state.complete();
        } else if (postcards.length < 5) {
          this.projects.push(...postcards);
          $state.complete();
        } else {
          this.projects.push(...postcards);
          $state.loaded();
        }
        this.pageNum++;
      } else {
        message.warn("获取数据失败");
      }
    },
    async loadPostcard(id) {
      const res = await getPostcard(id);
      if (res.code == 200) {
        this.emitter.emit("loadSpecificPostcard", {
          data: res.data,
        });
      } else {
        message.warn("载入明信片失败");
      }
    },
  },
};
</script>
<style lang="sass" scoped>
.my-postcards
  height: 100%
.scrollable
   height: 100%
.postcards
  &__container
      width: 100%
      text-align: center
      padding: 20px
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
