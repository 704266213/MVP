package com.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mvp.R
import com.mvp.model.CinemaModel

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/25 9:48
 * 修改人：admin
 * 修改时间：2017/12/25 9:48
 * 修改备注：
 * @version
 */
class CinemaAdapter : LoadMoreRecycleAdapter<CinemaModel>() {


    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): CinemaHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.cinema_item, parent, false)
        return CinemaHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int, entity: CinemaModel) {
        val cinemaHolder = viewHolder as CinemaHolder
        cinemaHolder.cinemaDistance.text = entity.cinemaDistance
        cinemaHolder.cinemaLocation.text = entity.cinemaLocal
        cinemaHolder.cinemaName.text = entity.cinemaName
        cinemaHolder.cinemaMinPrice.text = entity.cinemaMinPrice.toString()
    }


    class CinemaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cinemaDistance: TextView = itemView.findViewById(R.id.cinemaDistance)
        val cinemaLocation: TextView = itemView.findViewById(R.id.cinemaLocation)
        val cinemaName: TextView = itemView.findViewById(R.id.cinemaName)
        val cinemaMinPrice: TextView = itemView.findViewById(R.id.cinemaMinPrice)
    }

}