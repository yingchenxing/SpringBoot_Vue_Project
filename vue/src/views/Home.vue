<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 40px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid"/>Total number of users</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-money"/>Total sales</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">100</div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-bank-card"/>Income</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">$1000000</div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-s-shop"/>Number of stores</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">$100000</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height:400px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="width: 500px; height:400px"></div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data() {
    return {}
  },
  mounted() {//trigger after render

    var option = {
      title: {
        text: 'Members of each season',
        subtext: 'Trend Chart',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      xAxis: {
        type: 'category',
        data: ['Season one', 'Season two', 'Season three', 'Season four']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: "Starbucks",
          data: [],
          type: 'bar'
        },
        {
          name: "Starbucks",
          data: [],
          type: 'line'
        },
        {
          name: "Luckin Coffee",
          data: [],
          type: 'bar'
        },
        {
          name: "Luckin Coffee",
          data: [],
          type: 'line'
        }
      ]
    };

    var pieOption = {
      title: {
        text: 'Members of each season',
        subtext: 'Scale Chart',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Starbucks',
          type: 'pie',
          radius: '50%',
          center: ['25%', '50%'],
          label: {            //饼图图形上的文本标签
            normal: {
              show: true,
              position: 'inner', //标签的位置
              textStyle: {
                fontWeight: 300,
                fontSize: 14,    //文字的字体大小
                color: "#fff"
              },
              formatter: '{c}({d}%)'
            }
          },
          data: [],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
        {
          name: 'Luckin Coffee',
          type: 'pie',
          radius: '50%',
          center: ['75%', '50%'],
          label: {            //饼图图形上的文本标签
            normal: {
              show: true,
              position: 'inner', //标签的位置
              textStyle: {
                fontWeight: 300,
                fontSize: 14,    //文字的字体大小
                color: "#fff"
              },
              formatter: '{d}%'
            }
          },
          data: [
            {name: 'Season One', values: 1},
            {name: 'Season Two', values: 2},
            {name: 'Season Three', values: 3},
            {name: 'Season Four', values: 4},
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };

    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);

    this.request.get("/charts/members").then(res => {
      option.series[0].data = res.data
      option.series[1].data = res.data
      myChart.setOption(option);

      pieOption.series[0].data = [
        {name: 'Season One', value: res.data[0]},
        {name: 'Season Two', value: res.data[1]},
        {name: 'Season Three', value: res.data[2]},
        {name: 'Season Four', value: res.data[3]},
      ]
      pieOption.series[1].data = [
        {name: 'Season One', value: res.data[0]},
        {name: 'Season Two', value: res.data[1]},
        {name: 'Season Three', value: res.data[2]},
        {name: 'Season Four', value: res.data[3]},
      ]
      pieChart.setOption(pieOption);
    })
  }
}
</script>

<style scoped>

</style>