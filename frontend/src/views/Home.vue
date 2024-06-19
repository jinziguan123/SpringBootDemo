<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 20px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div>用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #f56c6c">
          <div>销售总量</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            ¥ 100000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67c23a">
          <div>收益总额</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            ¥ 30000000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #e6a23c">
          <div>门店数量</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 400px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 400px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'Home',
  data(){
    return {

    }
  },
  mounted(){
    // 页面元素渲染好再触发
    var chartDom = document.getElementById('main')
    var myChart = echarts.init(chartDom)
    var option = {
      title: {
        text: '也别管是什么了',
        subtext: '假的',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: ["第一季度","第二季度","第三季度","第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };

    this.request.get("/echarts/members").then(res => {
      option.series[0].data = res.data
      option.series[1].data = res.data
      myChart.setOption(option);
    })

    // 饼图
    var pieoption = {
      title: {
        text: 'Nightingale Chart',
        subtext: 'Fake Data',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        left: 'center',
        top: 'bottom',
        data: [
          'rose1',
          'rose2',
          'rose3',
          'rose4',
          'rose5',
          'rose6',
          'rose7',
          'rose8'
        ]
      },
      toolbox: {
        show: true,
        feature: {
          mark: { show: true },
          dataView: { show: true, readOnly: false },
          restore: { show: true },
          saveAsImage: { show: true }
        }
      },
      series: [
        {
          name: 'Radius Mode',
          type: 'pie',
          radius: [20, 140],
          center: ['25%', '50%'],
          roseType: 'radius',
          itemStyle: {
            borderRadius: 5
          },
          label: {
            show: false
          },
          emphasis: {
            label: {
              show: true
            }
          },
          data: [
            { value: 40, name: 'rose 1' },
            { value: 33, name: 'rose 2' },
            { value: 28, name: 'rose 3' },
            { value: 22, name: 'rose 4' },
            { value: 20, name: 'rose 5' },
            { value: 15, name: 'rose 6' },
            { value: 12, name: 'rose 7' },
            { value: 10, name: 'rose 8' }
          ]
        },
        {
          name: 'Area Mode',
          type: 'pie',
          radius: [20, 140],
          center: ['75%', '50%'],
          roseType: 'area',
          itemStyle: {
            borderRadius: 5
          },
          data: [
            { value: 30, name: 'rose 1' },
            { value: 28, name: 'rose 2' },
            { value: 26, name: 'rose 3' },
            { value: 24, name: 'rose 4' },
            { value: 22, name: 'rose 5' },
            { value: 20, name: 'rose 6' },
            { value: 18, name: 'rose 7' },
            { value: 16, name: 'rose 8' }
          ]
        }
      ]
    };
    var pieDom = document.getElementById('pie')
    var pieChart = echarts.init(pieDom)
    pieChart.setOption(pieoption);
  }
}

</script>

<style scoped>

</style>