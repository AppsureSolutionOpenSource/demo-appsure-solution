package com.appsuresolutions.utils.async;

import android.os.AsyncTask;

import com.appsuresolutions.utils.entrypoint.ICompletionListener;

public abstract class AsyncRunner<T> extends AsyncTask<Void,Void,Void> {
    private ICompletionListener<T> listener;
    private T success;
    private Throwable error;
    protected AsyncRunner(ICompletionListener<T> listener) {
        this.listener = listener;
    }

    public abstract T async() throws Throwable;
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            success = async();
        }catch (Throwable t){
            error = t;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(error != null){
            listener.onError(error);
        }else{
            listener.onSuccess(success);
        }
    }
}
