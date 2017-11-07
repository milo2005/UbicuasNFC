package unicauca.movil.museo.model

/**
 * Created by darfe on 7/11/2017.
 */
data class Pintura(val idpintura:Long,
                   val nombre:String,
                   val autor:String,
                   val tecnica:String,
                   val precio:Long,
                   val idnfc:String
                   )