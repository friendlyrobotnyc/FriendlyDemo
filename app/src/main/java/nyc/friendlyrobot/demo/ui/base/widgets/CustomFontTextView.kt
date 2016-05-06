package nyc.friendlyrobot.demo.ui.base.widgets

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import nyc.friendlyrobot.demo.androidboilerplate.R
import nyc.friendlyrobot.demo.ui.utils.TypefaceCache
import timber.log.Timber

/**
 * Created by brianplummer on 2/20/16.
 */
class CustomFontTextView
@SuppressWarnings("PMD.UnusedFormalParameter", "PMD.ConstructorCallsOverridableMethod")
constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : TextView(context, attrs) {

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs!!, R.style.TextView) {
    }

    init {
        paintFlags = paintFlags or Paint.SUBPIXEL_TEXT_FLAG

        var fontName: String? = null
        val appearance = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView)

        if (appearance != null) {
            val indexCount = appearance.indexCount
            for (i in 0..indexCount - 1) {
                val attr = appearance.getIndex(i)
                if (attr == R.styleable.CustomFontTextView_font) {
                    fontName = appearance.getString(attr)
                }
            }
            appearance.recycle()
        }
        setTypeface(fontName)
    }

    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    fun setTypeface(typefaceName: String?) {

        if (typefaceName == null) {
            return
        }

        val context = context
        var font: Typeface? = TypefaceCache.get(typefaceName)
        if (font == null) {
            try {
                font = Typeface.createFromAsset(context.assets, typefaceName)
            } catch (e: Exception) {
                Timber.e(CustomFontTextView::class.java.simpleName,
                        String.format("Error loading font: %s. Reverting to system default.", typefaceName), e)
                return
            }

            TypefaceCache.put(typefaceName, font)
        }
        typeface = font
    }

}
