package com.example.dimu_terkep.model

import android.app.Activity
import com.example.dimu_terkep.R

enum class IntezmenyTipus(val i:Int) {
    AllamiMuzeum(0),
    AllamiKuturalis(1),
    OnkormanyzatiMuzeum(2),
    OnkormanyzatiKulturalis(3),
    OnkormanyzatiGaleria(4),
    KereskedelmGaleria(5),
    FuggetlenKulturalisIntezmeny(6),
    NonProfitGaleria(7),
    KulturalisIntezet(8),
    Egyesulet(9),
    Oktatasi(10),
    EtteremKocsmaGaleria(11);

    companion object{
        fun desc(s:String, a:Activity): IntezmenyTipus{
            when(s){
                a.getString(R.string.type1) -> return AllamiMuzeum
                a.getString(R.string.type2) ->return AllamiKuturalis
                a.getString(R.string.type3) ->return OnkormanyzatiMuzeum
                a.getString(R.string.type4) ->return OnkormanyzatiKulturalis
                 a.getString(R.string.type5) ->return OnkormanyzatiGaleria
                a.getString(R.string.type6) ->return KereskedelmGaleria
                a.getString(R.string.type7) ->return FuggetlenKulturalisIntezmeny
                a.getString(R.string.type8) ->return NonProfitGaleria
                a.getString(R.string.type9) ->return KulturalisIntezet
                a.getString(R.string.type10) ->return Egyesulet
                a.getString(R.string.type11) ->return Oktatasi
                a.getString(R.string.type12) ->return EtteremKocsmaGaleria
                else -> return AllamiMuzeum
            }
        }

    }

}