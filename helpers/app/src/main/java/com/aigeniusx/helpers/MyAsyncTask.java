package com.aigeniusx.helpers;

public class MyAsyncTask extends AsyncTask<Void, Void, ResultType> {
    @Override
    protected ResultType doInBackground(Void... voids) {
        // Perform network operation in the background
        return performNetworkOperation();
    }

    @Override
    protected void onPostExecute(ResultType result) {
        // Update the UI with the result
    }
}


