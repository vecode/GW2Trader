﻿using System;
using System.Globalization;
using System.Windows.Data;
using System.Windows.Media;

namespace GW2Trader.Desktop.Util
{
    public class RarityToBrushConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value == null)
            {
                return Brushes.White;
            }

            var brush = new SolidColorBrush();
            string hexCode;
            switch (value.ToString())
            {
                case "Junk":
                    return Brushes.LightGray;
                case "Basic":
                    return Brushes.LightGray;
                case "Fine":
                    hexCode = "#62a4da";
                    break;
                case "Masterwork":
                    hexCode = "#1a9306";
                    break;
                case "Rare":
                    hexCode = "#fcd00b";
                    break;
                case "Exotic":
                    hexCode = "#ffa405";
                    break;
                case "Ascended":
                    hexCode = "#fb3e8d";
                    break;
                case "Legendary":
                    hexCode = "#4c139d";
                    break;
                default:
                    return Brushes.White;
            }
            return (SolidColorBrush) (new BrushConverter().ConvertFrom(hexCode));
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}