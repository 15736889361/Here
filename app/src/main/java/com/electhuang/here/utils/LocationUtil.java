package com.electhuang.here.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

/**
 * 定位工具类
 */
public class LocationUtil {

	private BaiduMap mBaiduMap;
	private LocationClient mLocationClient;
	private Context context;
	private BDLocation mLocation = null;
	private double mLatitude;//纬度
	private double mLongitude;//经度
	OnInitLocationListener mInitLocationListener;

	public BDLocation getmLocation() {
		return mLocation;
	}

	public double getmLongitude() {
		return mLongitude;
	}

	public double getmLatitude() {
		return mLatitude;
	}

	public LocationUtil(Context context, BaiduMap baiduMap, LocationClient locationClient) {
		this.mBaiduMap = baiduMap;
		this.mLocationClient = locationClient;
		this.context = context;
	}

	/**
	 * 初始化地图显示
	 */
	public void initLocation(OnInitLocationListener initLocationListener) {
		this.mInitLocationListener = initLocationListener;
		//开启定位
		mBaiduMap.setMyLocationEnabled(true);
		MyLocationListener listener = new MyLocationListener();
		//注册监听函数
		mLocationClient.registerLocationListener(listener);

		//配置定位SDK各配置参数，比如定位模式、定位时间间隔、坐标系类型等
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
		option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
		//可选，默认false，设置是否需要位置语义化结果，可以在BDLocationget LocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationDescribe(true);
		option.setNeedDeviceDirect(true);
		option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

		mLocationClient.setLocOption(option);
	}

	/**
	 * 定位监听函数
	 */
	public class MyLocationListener implements BDLocationListener {

		private boolean isFirstIn = true;

		@Override
		public void onReceiveLocation(BDLocation bdLocation) {
			/*
			* 可以通过BDLocation配置如下参数
            * 1.accuracy 定位精度
            * 2.latitude 百度纬度坐标
            * 3.longitude 百度经度坐标
            * 4.satellitesNum GPS定位时卫星数目 getSatelliteNumber() gps定位结果时，获取gps锁定用的卫星数
            * 5.speed GPS定位时速度 getSpeed()获取速度，仅gps定位结果时有速度信息，单位公里/小时，默认值0.0f
            * 6.direction GPS定位时方向角度
            */
			double latitude = bdLocation.getLatitude();
			double longitude = bdLocation.getLongitude();

			mLocation = bdLocation;
			mLatitude = latitude;
			mLongitude = longitude;

			Log.e("TAG", "详细地址:" + bdLocation.getLocType() + "-" + bdLocation.getLocationDescribe());
			MyLocationData locationData = new MyLocationData.Builder().accuracy(bdLocation.getRadius()).direction
					(bdLocation.getDirection()).latitude(latitude).longitude(longitude).build();
			//设置定位数据, 只有先允许定位图层后设置数据才会生效，参见 setMyLocationEnabled(boolean)
			mBaiduMap.setMyLocationData(locationData);
			mBaiduMap.setMaxAndMinZoomLevel(21, 18);
			//配置定位图层显示方式,三个参数的构造器
			//BitmapDescriptor currentMarker = BitmapDescriptorFactory.fromResource(R.drawable.current);
			MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode
					.FOLLOWING, true, null);
			mBaiduMap.setMyLocationConfigeration(configuration);

			//判断是否为第一次定位,是的话需要定位到用户当前位置
			if (isFirstIn) {
				/*//地理坐标基本数据结构
				LatLng latLng = new LatLng(latitude, longitude);
				MapStatus status = new MapStatus.Builder().target(latLng).zoom(20).build();
				//描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
				MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(status);

				//改变地图状态
				mBaiduMap.setMapStatus(msu);*/
				MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(20);
				mBaiduMap.animateMapStatus(msu);
				isFirstIn = false;
				Toast.makeText(context, bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
			}
			mInitLocationListener.initSucceed();
		}
	}

	public interface OnInitLocationListener{

		void initSucceed();
	}
}
