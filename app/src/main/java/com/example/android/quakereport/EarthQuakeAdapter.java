package com.example.android.quakereport;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.drawable.GradientDrawable;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    public EarthQuakeAdapter(Activity context, ArrayList<EarthQuake> earthQuakes) {
        super(context, 0, earthQuakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        EarthQuake currentEarthQuake = getItem(position);
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeTextView.setText(formatMagnitude(currentEarthQuake.getMagnitude()));
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = ContextCompat.getColor(getContext(), getMagnitudeColor(currentEarthQuake.getMagnitude()));

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        String tLocation = currentEarthQuake.getLocation();
        int index = tLocation.indexOf("of");
        String offset, location;
        if (index == -1) {
            offset = "Near the";
            location = tLocation;
        } else {
            offset = tLocation.substring(0, index) + "of";
            location = tLocation.substring(index + 3);
        }

        TextView offsetTextView = (TextView) listItemView.findViewById(R.id.offset);
        offsetTextView.setText(offset);

        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location);
        locationTextView.setText(location);

        Date dateObject = new Date(currentEarthQuake.getTimeInMilliseconds());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        dateTextView.setText(formattedDate);

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeTextView.setText(formattedTime);


        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        if (magnitude > 10) return R.color.magnitude10plus;
        else if (magnitude <= 10 && magnitude > 9) return R.color.magnitude9;
        else if (magnitude <= 9 && magnitude > 8) return R.color.magnitude8;
        else if (magnitude <= 8 && magnitude > 7) return R.color.magnitude7;
        else if (magnitude <= 7 && magnitude > 6) return R.color.magnitude6;
        else if (magnitude <= 6 && magnitude > 5) return R.color.magnitude5;
        else if (magnitude <= 5 && magnitude > 4) return R.color.magnitude4;
        else if (magnitude <= 4 && magnitude > 3) return R.color.magnitude3;
        else if (magnitude <= 3 && magnitude > 2) return R.color.magnitude2;
        else if (magnitude <= 2 && magnitude > 0) return R.color.magnitude1;
        else return R.color.magnitude1;
    }
}
