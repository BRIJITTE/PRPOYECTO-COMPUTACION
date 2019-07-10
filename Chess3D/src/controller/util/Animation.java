package controller.util;

/**
* Define una animación básica de interpolación lineal, que se actualiza.
  * por un delta_t en el gameloop. Esta clase trabaja llamando
  * el método setValue () del objeto Animatable pasado en
  *
 */
public class Animation implements Comparable<Animation> {

	private Animatable object;
	private String fieldName;
	
	private float value;
	private float endValue;
	
	private float valueStep;
	
	/**
	* Construye un objeto de animación que establecerá continuamente el valor
        * del objeto pasado.
	 * 
	 * @param _object Objeto en el que se realiza la animación
         * @param _name El nombre del campo en el objeto que se está actualizando.
         * @param _startVal El valor inicial del campo del objeto
         * @param _endVal El valor final del campo del objeto
         * @param _time El tiempo de la animación.
	 */
	public Animation(Animatable _object, String _fieledName, float _startVal, float _endVal, float _time) {
		object = _object;
		fieldName = _fieledName;
		
		value = _startVal;
		endValue = _endVal;
		
		valueStep = (_endVal - _startVal) / _time;
		_object.setValue(_fieledName, _startVal);
	}
	
	/**
	* Pasa esta animación por _deltaTime, lo que resultará en el objeto
        * valor siendo actualizado por + valueStep
        *
	 * @param _deltaTime El paso del tiempo
       * @return true si la animación ha finalizado, false en caso contrario
	 */
	public boolean stepAnimation(float _deltaTime) {
		// actualizar valor
		value += valueStep * _deltaTime;
		
		//Determinar si la animación ha pasado el valor final.
		float val_sign = Math.signum(valueStep);
		if (value * val_sign >= endValue * val_sign) {
			object.setValue(fieldName, endValue);
			return true;
		}
		
		//establecer el campo del objeto en valor
		object.setValue(fieldName, value);
		return false;
	}
	
	@Override
	public int compareTo(Animation arg0) {
		return hashCode() - arg0.hashCode();
	}
	
	@Override
	public boolean equals(Object arg0) {
		return hashCode() == arg0.hashCode();
	}
	

	/**
	 * Interfaz que debe ser implementada por cualquier objeto.
         * Que quiere ser animado.
	 *
	 */
	public interface Animatable {
		
		void setValue(String fieldName, float value);
		
	}
	
}
