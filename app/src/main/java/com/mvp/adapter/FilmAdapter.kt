package com.mvp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mvp.MVPApplication
import com.mvp.R
import com.mvp.model.FilmInfoModel

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 16:50
 * 修改人：admin
 * 修改时间：2017/12/13 16:50
 * 修改备注：
 * @version
 */
class FilmAdapter(private val context: Context) : LoadMoreRecycleAdapter<FilmInfoModel>() {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.film_item, parent, false)
        return FilmHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int, entity: FilmInfoModel) {
        val filmHolder = viewHolder as FilmHolder
        filmHolder.filmName.text = entity.filmName
        filmHolder.filmActor.text = entity.filmActor
        filmHolder.filmScore.text = entity.filmScore
        filmHolder.filmDesc.text = entity.filmDesc

        Glide.with(context)
                .load(entity.filmUrl)
                .into(filmHolder.filmUrl)
    }


    class FilmHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filmUrl: ImageView = itemView.findViewById(R.id.filmUrl)
        val filmName: TextView = itemView.findViewById(R.id.filmName)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val filmActor: TextView = itemView.findViewById(R.id.filmActor)
        val filmScore: TextView = itemView.findViewById(R.id.filmScore)
        val filmDesc: TextView = itemView.findViewById(R.id.filmDesc)
    }

}