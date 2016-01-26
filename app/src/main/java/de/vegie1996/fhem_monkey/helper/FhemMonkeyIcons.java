/*
 * Copyright 2014 Mike Penz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.vegie1996.fhem_monkey.helper;

import android.content.Context;
import android.graphics.Typeface;

import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.typeface.ITypeface;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class FhemMonkeyIcons implements ITypeface {
    private static final String TTF_FILE = "iconfont.ttf";
    private static Typeface typeface = null;
    private static HashMap<String, Character> mChars;

    @Override
    public IIcon getIcon(String key) {
        return Icon.valueOf(key);
    }

    @Override
    public HashMap<String, Character> getCharacters() {
        if (mChars == null) {
            HashMap<String, Character> aChars = new HashMap<String, Character>();
            for (Icon v : Icon.values()) {
                aChars.put(v.name(), v.character);
            }
            mChars = aChars;
        }
        return mChars;
    }

    @Override
    public String getMappingPrefix() {
        return "de.vegie1996.fhem_monkey";
    }

    @Override
    public String getFontName() {
        return "";
    }

    @Override
    public String getVersion() {
        return "";
    }

    @Override
    public int getIconCount() {
        return mChars.size();
    }

    @Override
    public Collection<String> getIcons() {
        Collection<String> icons = new LinkedList<String>();
        for (Icon value : Icon.values()) {
            icons.add(value.name());
        }
        return icons;
    }

    @Override
    public String getAuthor() {
        return "";
    }

    @Override
    public String getUrl() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getLicense() {
        return "";
    }

    @Override
    public String getLicenseUrl() {
        return "";
    }

    @Override
    public Typeface getTypeface(Context context) {
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + TTF_FILE);
            } catch (Exception e) {
                return null;
            }
        }
        return typeface;
    }

    public enum Icon implements IIcon {
        _advertising2('\ue000'),
		_architecture4('\ue001'),
		_architecture5('\ue002'),
		_arrow116('\ue003'),
		_arrows149('\ue004'),
		_arrows151('\ue005'),
		_bank24('\ue006'),
		_barrow3('\ue007'),
		_bath10('\ue008'),
		_beds2('\ue009'),
		_bricks11('\ue00a'),
		_building112('\ue00b'),
		_building113('\ue00c'),
		_building114('\ue00d'),
		_building2('\ue00e'),
		_buildings25('\ue00f'),
		_bulbs1('\ue010'),
		_camp('\ue011'),
		_cardboard34('\ue012'),
		_cars5('\ue013'),
		_chairs1('\ue014'),
		_city3('\ue015'),
		_city5('\ue016'),
		_clips('\ue017'),
		_coin('\ue018'),
		_columns7('\ue019'),
		_construction36('\ue01a'),
		_construction37('\ue01b'),
		_construction38('\ue01c'),
		_construction39('\ue01d'),
		_construction41('\ue01e'),
		_construction42('\ue01f'),
		_couch5('\ue020'),
		_cups7('\ue021'),
		_currency17('\ue022'),
		_daily21('\ue023'),
		_device17('\ue024'),
		_direction65('\ue025'),
		_documents32('\ue026'),
		_door2('\ue027'),
		_doors3('\ue028'),
		_draw27('\ue029'),
		_earphones7('\ue02a'),
		_exam('\ue02b'),
		_eyeglasses('\ue02c'),
		_factory27('\ue02d'),
		_fan1('\ue02e'),
		_fire1('\ue02f'),
		_floor('\ue030'),
		_fountain7('\ue031'),
		_furniture20('\ue032'),
		_garden10('\ue033'),
		_garden11('\ue034'),
		_garden12('\ue035'),
		_graphs4('\ue036'),
		_hacksaw('\ue037'),
		_hammers1('\ue038'),
		_hand260('\ue039'),
		_home19('\ue03a'),
		_horn('\ue03b'),
		_hotels('\ue03c'),
		_house222('\ue03d'),
		_house223('\ue03e'),
		_house224('\ue03f'),
		_house225('\ue040'),
		_house226('\ue041'),
		_house227('\ue042'),
		_house228('\ue043'),
		_house229('\ue044'),
		_house230('\ue045'),
		_house231('\ue046'),
		_house232('\ue047'),
		_indoor2('\ue048'),
		_interiordesign('\ue049'),
		_key19('\ue04a'),
		_key21('\ue04b'),
		_lamp4('\ue04c'),
		_laptop9('\ue04d'),
		_letter121('\ue04e'),
		_lift2('\ue04f'),
		_light112('\ue050'),
		_lock107('\ue051'),
		_locked72('\ue052'),
		_luggage20('\ue053'),
		_machine3('\ue054'),
		_machine('\ue055'),
		_mail15('\ue056'),
		_medicine3('\ue057'),
		_money207('\ue058'),
		_money210('\ue059'),
		_news3('\ue05a'),
		_notdisturb('\ue05b'),
		_officematerial('\ue05c'),
		_open10('\ue05d'),
		_order5('\ue05e'),
		_padlock10('\ue05f'),
		_parking1('\ue060'),
		_pen12('\ue061'),
		_pin95('\ue062'),
		_pin96('\ue063'),
		_plumber1('\ue064'),
		_print62('\ue065'),
		_prints1('\ue066'),
		_purse12('\ue067'),
		_radiator('\ue068'),
		_realestate1('\ue069'),
		_realestate2('\ue06a'),
		_realestate3('\ue06b'),
		_repair23('\ue06c'),
		_repair24('\ue06d'),
		_repair25('\ue06e'),
		_roller('\ue06f'),
		_ruler30('\ue070'),
		_sandclock('\ue071'),
		_scissors2('\ue072'),
		_search9('\ue073'),
		_shopcart('\ue074'),
		_shoppingstore2('\ue075'),
		_shovel21('\ue076'),
		_shower1('\ue077'),
		_signal56('\ue078'),
		_signal58('\ue079'),
		_signal60('\ue07a'),
		_socket4('\ue07b'),
		_speakers2('\ue07c'),
		_stats25('\ue07d'),
		_steps2('\ue07e'),
		_suitcase59('\ue07f'),
		_swim5('\ue080'),
		_switch3('\ue081'),
		_telephone146('\ue082'),
		_telephone148('\ue083'),
		_television34('\ue084'),
		_temperature1('\ue085'),
		_terrace('\ue086'),
		_title('\ue087'),
		_toilet16('\ue088'),
		_travel1('\ue089'),
		_trees16('\ue08a'),
		_uparrow25('\ue08b'),
		_uparrow26('\ue08c'),
		_vacuum1('\ue08d'),
		_wash6('\ue08e'),
		_water78('\ue08f'),
		_weights('\ue090'),
		_wheelbarrow('\ue091'),
		_window81('\ue092'),
		_work24('\ue093'),
		_work25('\ue094'),
		_work27('\ue095');

        char character;

        Icon(char character) {
            this.character = character;
        }

        public String getFormattedName() {
            return "{" + name() + "}";
        }

        public char getCharacter() {
            return character;
        }

        public String getName() {
            return name();
        }

        // remember the typeface so we can use it later
        private static ITypeface typeface;

        public ITypeface getTypeface() {
            if (typeface == null) {
                typeface = new FhemMonkeyIcons();
            }
            return typeface;
        }
    }
}
