package whizz.me.calculator.utils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;

import whizz.me.calculator.calculator.Parser;

public class Clipboard {
    private ClipboardManager  clipboard;

    public Clipboard(ClipboardManager clipboard){
        this.clipboard = clipboard;

    }

    public boolean canClip() {
        boolean canClip;
        if(!clipboard.hasPrimaryClip()){//if clipboard is not empty
            canClip = false;
        }
        else if(!clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){//if clipboard has text
            canClip = false;
        }

        /*else if(clipboard.getPrimaryClip().getItemAt(0).getText().toString().isEmpty()){//if the text in the clipboard is not empty //TODO test if works without this
            canClip = false;
        }*/
        else if(!Parser.isOperand(clipboard.getPrimaryClip().getItemAt(0).getText().toString())){//if the text is a number
            canClip = false;
        }
        else{
            canClip = true;
        }
        return canClip;
    }

    public void copy(String toCopy){
        ClipData clip = ClipData.newPlainText(ClipDescription.MIMETYPE_TEXT_PLAIN, toCopy);//Adds the toCopy to the clipboard
        clipboard.setPrimaryClip(clip);//makes the new clip at the top of the clipboard
    }
    public String paste(int pos,String oldText){//TODO review null condition
        ClipData.Item clipItem = clipboard.getPrimaryClip().getItemAt(0);//Assuming there is something to paste
        String pasteData = clipItem.getText().toString();
        if(pasteData != null){//if null then the data is a URI
            return PasteUtils.pastInto(oldText,pasteData,pos);
        }
        return null;
    }

}
